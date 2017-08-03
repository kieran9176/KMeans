
import java.util.Scanner;

class listcell {

    public int obs_num;
    public float sep_length, sep_width, pet_length, pet_width;
    public listcell next, prev;

    public listcell()
    {
        next = null;
        prev = null;
        obs_num = 0;
        sep_length = 0;
        sep_width = 0;
        pet_length = 0;
        pet_width = 0;
    }

    public void setNext(listcell n) {
        next = n;
    }
    public void setPrev(listcell p) {
        prev = p;
    }

    public listcell getNext() {
        return next;
    }
    public listcell getPrev() {
        return prev;
    }
}

class dll {

    public int size;
    protected listcell head;
    protected listcell tail;

    public dll()
    {
        head = null;
        tail = null;
        size = 0;

    }

    public void prepend (listcell new_cell) {

        if (head == null) {
            head = new_cell;
            tail = head;
        }
        else {
            head.setPrev(new_cell);
            new_cell.setNext(head);
            head = new_cell;
        }
        size++;
    }

    public void append (listcell new_cell) {

        if (head == null) {
            head = new_cell;
            tail = new_cell;
        }

        else {
            tail.setNext(new_cell);

            new_cell.setPrev(tail);
            new_cell.setNext(null);

            tail = new_cell;
        }
        size++;
    }

    public listcell search (int t) {
        listcell x = head;
        while (x != null) {
            if (t == x.obs_num) {
                return x;
            } else {
                x = x.getNext();
            }
        }
        return null;
    }

    public void delete (listcell x) {

            if (size == 1) {
                head = null;
                tail = null;
                size = 0;

            } else if (x.getNext() == null) {
                tail = x.getPrev();
                tail.setNext(null);
                size--;

            } else if (x.getPrev() == null) {
                head = x.getNext();
                head.setPrev(null);
                size--;
            }
            else {
                listcell prev = x.getPrev();
                listcell next = x.getNext();

                next.setPrev(prev);

                prev.setNext(next);
                size--;
        }
    }
}


