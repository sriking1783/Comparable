public class MaxArrayList61B<E extends Comparable<E>> extends ArrayList61B<MaxArrayList61B<E>> implements Comparable<MaxArrayList61B<E>>  {

	@Override
    public int compareTo(MaxArrayList61B<E> other){
    	MaxArrayList61B<E> temp;
    	int i;
    	System.out.println(size());
    	for(i = 0; i < size(); i++ ){
    		temp = get(i);
    		if(temp == null) break;
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
    public boolean add(MaxArrayList61B<E> item){
		if(compareTo(item) > 0){
			super.add(item);
			return true;
		}
		else{
			return false;
		}
	}
}
