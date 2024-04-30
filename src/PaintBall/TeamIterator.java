package PaintBall;

public class TeamIterator implements Iterator<Team> {
    private int len;
    private TeamArrayList array;
    private int index;

    TeamIterator(TeamArrayList array) {
        this.array = array;
        index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < array.len();
    }

    @Override
    public Team next() {
        return array.get(index++);
    }
}