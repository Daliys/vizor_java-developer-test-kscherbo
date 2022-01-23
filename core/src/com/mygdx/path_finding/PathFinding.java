package com.mygdx.path_finding;

import com.badlogic.gdx.ai.pfa.*;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class PathFinding {
    public final AStarMap map;
    private final PathFinder<Node> pathfinder;
    private final Heuristic<Node> heuristic;
    private final GraphPath<Connection<Node>> connectionPath;
    public int nodeCounter = 0;
    public static float scaleMapToWorldScale = 10;

    public PathFinding(AStarMap map) {
        this.map = map;
        this.pathfinder = new IndexedAStarPathFinder<Node>(createGraph(map));
        this.connectionPath = new DefaultGraphPath<Connection<Node>>();
        this.heuristic = new Heuristic<Node>() {
            @Override
            public float estimate (Node node, Node endNode) {
                // Manhattan distance
                return Math.abs(endNode.x - node.x) + Math.abs(endNode.y - node.y);
            }
        };
    }

    public Node getNextNode() {
        return connectionPath.getCount() <= nodeCounter ? null : connectionPath.get(nodeCounter).getToNode();
    }

    public Node getLastNode(){
        return connectionPath.getCount() <= nodeCounter? null : connectionPath.get(connectionPath.getCount()-1).getToNode();
    }

    public GraphPath<Connection<Node>> getFullConnectionPath(){
        return connectionPath;
    }

    public void findPath(Vector2 source, Vector2 target){
        nodeCounter = 0;
        source = convertToMapCoordinate(source);
        target = convertToMapCoordinate(target);

        int sourceX = MathUtils.floor(source.x);
        int sourceY = MathUtils.floor(source.y);
        int targetX = MathUtils.floor(target.x);
        int targetY = MathUtils.floor(target.y);
        connectionPath.clear();

        if (map == null
                || sourceX < 0 || sourceX >= map.getWidth()
                || sourceY < 0 || sourceY >= map.getHeight()
                || targetX < 0 || targetX >= map.getWidth()
                || targetY < 0 || targetY >= map.getHeight()) {
            return;
        }

        Node sourceNode = map.getNodeAt(sourceX, sourceY);
        Node targetNode = map.getNodeAt(targetX, targetY);
        pathfinder.searchConnectionPath(sourceNode, targetNode, heuristic, connectionPath);
        pathOptimization();
    }

    private void pathOptimization(){
        GraphPath<Connection<Node>> newPath = new DefaultGraphPath<Connection<Node>>();

        int x, y;
        int x1, y1;
        for(int i = 0; i < connectionPath.getCount(); i++){
            if(i == 0) newPath.add(connectionPath.get(0));
            x = connectionPath.get(i).getToNode().x;
            y = connectionPath.get(i).getToNode().y;

            if((i+2) >= connectionPath.getCount()) {
                if(newPath.get(newPath.getCount()-1).getToNode().x != connectionPath.get(i).getToNode().x ||
                        newPath.get(newPath.getCount()-1).getToNode().y != connectionPath.get(i).getToNode().y ) {
                    newPath.add(connectionPath.get(i));
                }
                continue;
            }
            x1= connectionPath.get(i+2).getToNode().x;
            y1 = connectionPath.get(i+2).getToNode().y;

            if ((x1==(x+1)  && y1 == (y+1)) || (x1 == (x+1) && y1 == (y-1)) ||
                    (x1 == (x-1) && y1 == (y-1)) || (x1 == (x-1) && y1 == (y+1))){
                if(newPath.get(newPath.getCount()-1).getToNode().x != connectionPath.get(i).getToNode().x ||
                        newPath.get(newPath.getCount()-1).getToNode().y != connectionPath.get(i).getToNode().y ) {
                    newPath.add(connectionPath.get(i));
                }
                newPath.add(connectionPath.get(i+2));
                i++;
            }else{
                if(newPath.get(newPath.getCount()-1).getToNode().x != connectionPath.get(i).getToNode().x ||
                        newPath.get(newPath.getCount()-1).getToNode().y != connectionPath.get(i).getToNode().y ) {
                    newPath.add(connectionPath.get(i));
                }
            }
        }
        connectionPath.clear();
        for(Connection<Node> node : newPath){
            connectionPath.add(node);
        }
    }

    public Vector2 convertToMapCoordinate(Vector2 position){
        Vector2 pos = new Vector2();
        pos.x = (int) position.x/10;
        pos.y = (int) position.y/10;
        return pos;
    }

    private static final int[][] NEIGHBORHOOD = new int[][] {
            new int[] {-1,  0},
            new int[] { 0, -1},
            new int[] { 0,  1},
            new int[] { 1,  0}
    };

    public static MyGraph createGraph (AStarMap map) {
        final int height = map.getHeight();
        final int width = map.getWidth();
        MyGraph graph = new MyGraph(map);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Node node = map.getNodeAt(x, y);
                if (node.isGameObject) {
                    continue;
                }
                // Add a connection for each valid neighbor
                for (int offset = 0; offset < NEIGHBORHOOD.length; offset++) {
                    int neighborX = node.x + NEIGHBORHOOD[offset][0];
                    int neighborY = node.y + NEIGHBORHOOD[offset][1];
                    if (neighborX >= 0 && neighborX < width && neighborY >= 0 && neighborY < height) {
                        Node neighbor = map.getNodeAt(neighborX, neighborY);
                        if (!neighbor.isGameObject) {
                            // Add connection to walkable neighbor
                            node.getConnections().add(new DefaultConnection<Node>(node, neighbor));
                        }
                    }
                }
                node.getConnections().shuffle();
            }
        }
        return graph;
    }

    private static class MyGraph implements IndexedGraph<Node> {

        AStarMap map;

        public MyGraph (AStarMap map) {
            this.map = map;
        }

        @Override
        public int getIndex(Node node) {
            return node.getIndex();
        }

        @Override
        public Array<Connection<Node>> getConnections(Node fromNode) {
            return fromNode.getConnections();
        }

        @Override
        public int getNodeCount() {
            return map.getHeight() * map.getWidth();
        }

    }

}
