import java.util.AbstractList;
public class ArrayList61B<E> extends AbstractList<E>{
	private E[] items;
	private int size;

	public ArrayList61B(int initialCapacity){
		if(initialCapacity < 1) throw new IllegalArgumentException("initialCapacity must be more than 1");

		size = initialCapacity;
		items =  (E[]) new Object[size];
	}

	public ArrayList61B(){
		this(1);
	}

	public E get(int i){
		if((i < 0) && (i> size)) throw new IllegalArgumentException("index not reachable");
		return items[i];
	}

	public boolean add(E item){
		size = size + 1;
		E[] newItems = (E[]) new Object[size];
		int i = 0;
		for (; i < items.length-1; i += 1) {
            newItems[i] = items[i];
        }
        newItems[i] = item;
        items = newItems;
        return true;

	}

	public int size(){
		return size;
	}
}