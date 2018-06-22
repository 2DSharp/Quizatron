package me.twodee.quizatron.Model.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BlockSet implements Serializable {

    private List<Block> blockList = new ArrayList<>(6);
    private String image;

    public List<Block> getBlockList() {
        return blockList;
    }

    public void setBlockList(List<Block> blockList) {
        this.blockList = blockList;
    }

    public Block getBlock(int index) {
        return blockList.get(index);
    }

    public void setBlock(int index, Block block) {

        try {
            blockList.set(index, block);
        } catch ( IndexOutOfBoundsException e ) {
            blockList.add( index, block );
        }

    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
