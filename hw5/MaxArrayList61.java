public class MaxArrayList61<E extends Comparable<E>> extends ArrayList61B<E> implements Comparable<E> {
	@Override
    public int compareTo(E other){
    	E temp;
    	int i;
    	for(i = 0; i < size() - 1; i++ ){
    		temp = get(i);
			if(other.compareTo(temp) < 0){
				return -1;
			}
			else if (temp.compareTo(other) == 0) {
				return 0;
			}
    	}
    	return 1;
    }

    @Override
    public boolean add(E item){
		if(compareTo(item) > 0){
			super.add(item);
			return true;
		}
		else{
			return false;
		}
	}
}