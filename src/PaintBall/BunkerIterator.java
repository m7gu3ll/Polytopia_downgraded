package PaintBall;

public class BunkerIterator implements Iterator<Tile> {
    BunkerArrayList array;
    int index;

    BunkerIterator(BunkerArrayList array) {
        this.array = array;
        index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < array.len();
    }

    @Override
    public Tile next() {
        return array.get(index++);
    }
}
