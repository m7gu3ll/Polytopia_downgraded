package PaintBall;

public class PlayerIterator implements Iterator<Player> {
    PlayerArrayList array;
    int index;

    public PlayerIterator(PlayerArrayList array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return index < array.len();
    }

    @Override
    public Player next() {
        return array.get(index++);
    }
}
