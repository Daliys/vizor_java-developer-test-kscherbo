package com.mygdx.woodcuter_controller;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game_objects.Tree;

public class TreeKeeper {
    private static TreeKeeper instance;
    private Array<TreeHandler> treeArray;

    public TreeKeeper(){
        treeArray = new Array<>();
    }

    public void addTree(Tree tree){
        treeArray.add(new TreeHandler(tree, null));
    }

    public TreeHandler getRandomTree(){
        return treeArray.random();
    }

    public static TreeKeeper getInstance() {
        if (instance == null) {
            instance = new TreeKeeper();
        }
        return instance;
    }

    public void removeTree(WoodCutterController controller){
        for (int i =0; i < treeArray.size; i++){
            if(treeArray.get(i).woodCutterController == (controller)) {
                treeArray.removeIndex(i);
                return;
            }
        }
    }

    public TreeHandler getTree(Tree tree){
        for (int i =0; i < treeArray.size; i++){
            if(treeArray.get(i).tree == (tree)) {
                return treeArray.get(i);
            }
        }
        return null;
    }

    public class TreeHandler{
        public Tree tree;
        public WoodCutterController woodCutterController;

        public TreeHandler(Tree tree, WoodCutterController controller){
            this.tree = tree;
            this.woodCutterController = controller;
        }
    }

}

