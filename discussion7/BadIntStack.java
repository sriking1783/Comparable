class BadIntStack {
    SNode top;
    final int capacity=10;
    int fillCount;
    public boolean isEmpty() {
        return top == null;
    }

    public boolean isFull() {
        return fillCount == capacity;
    }

    public void push(Integer num) {
      if(isFull()){
          return;
      }
      else{
          top = new SNode(num, top);
          fillCount++;
      }
    }

    public Integer pop() {
        if(isEmpty()){
            return null;
        }
        else{
            Integer ans = top.val;
            top = top.prev;
            return ans;
        }
    }

    public Integer peek() {
        return top.val;
    }
 }
