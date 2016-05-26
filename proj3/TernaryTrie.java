import java.util.*;

public class TernaryTrie {
    private TrieNode root;
    PriorityQueue<TrieNode> pq;
    private class TrieNode {
        char c;
        TrieNode left, mid, right;
        boolean end;
        boolean visited;
        double weight;
        double max_weight;
        public TrieNode() {

        }

        public TrieNode(char ch) {
            this.c = ch;
        }
    }


    public Double findWeight(String word) {
        return findWeight(root, word, 0);
    }

    public Double findWeight(TrieNode node, String word, int index) {
        if(node == null) return 0.0;
        char ch = word.charAt(index);
        if(ch < node.c) return  findWeight(node.left, word, index);
        if(ch > node.c) return  findWeight(node.right, word, index);
        if(index < word.length() - 1) return  findWeight(node.mid, word, index+1);
        return node.weight;
    }

    public HashMap<String, Double> findMatches(String prefix, int k) {
        pq = new PriorityQueue<TrieNode>(k, new Comparator<TrieNode>() {
			@Override
			public int compare(TrieNode o1, TrieNode o2) {
				// TODO Auto-generated method stub
				return (int) Math.round(o2.max_weight - o1.max_weight);
			}
		});

        HashMap<String, Double> matched_words = new HashMap<String, Double>();
        String str = "";
        TrieNode temp = root;
        for(int i = 0; i < prefix.length()  && temp != null; i++){
            char ch = prefix.charAt(i);

            //System.out.println(temp.c);
            if(ch < temp.c) {
                i -= 1;
                temp = temp.left;
            }
            else if(ch > temp.c) {
                i -= 1;
                temp = temp.right;
            }
            else if(i < prefix.length() - 1) {
                temp = temp.mid;
            }
        }
        //temp = temp.mid;
        str = str + prefix ;//+ temp.c;

        bfs(temp, str, matched_words);

        List<Map.Entry<String, Double>> list =
            new LinkedList<Map.Entry<String, Double>>(matched_words.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Double>>(){
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2){
                return(o1.getValue()).compareTo(o2.getValue());
            }
        });

        Collections.reverse(list);
        HashMap<String, Double> sortedMatches = new LinkedHashMap<String, Double>();
        for(Iterator<Map.Entry<String, Double>> it = list.iterator(); it.hasNext();){
            Map.Entry<String, Double> entry = it.next();
            sortedMatches.put(entry.getKey(), entry.getValue());
        }

        return sortedMatches;
    }

    public void bfs(TrieNode node, String str, HashMap<String, Double> matched_words)
	{
		// BFS uses Queue data structure
		Queue queue = new Queue();
		queue.enqueue(node);

		node.visited = true;
        int i = 0;
		while(!queue.isEmpty()) {
			TrieNode temp = (TrieNode)queue.dequeue();
			TrieNode child = null;

			while((child = getUnvisitedChildNode(temp))!=null) {
				child.visited = true;

				pq.add(child);
				queue.enqueue(child);
                if(i>0){
                    System.out.println(temp.c);
                    str = str + temp.c;
                    //getFromPQ(str, matched_words);
                    System.out.println(str);
                    bfs(child, str + temp.c, matched_words);
                    i = 0;
                }
                else{
                    getFromPQ(str, matched_words);
                }
			}
            i++;

		}

        queue.enqueue(node);
        node.visited = false;
		while(!queue.isEmpty()) {
            TrieNode temp = (TrieNode)queue.dequeue();
			TrieNode child = null;

			while((child = getUnvisitedChildNode(temp))!=null) {
                child.visited = false;
                queue.enqueue(child);
            }
        }
	}

    private void getFromPQ(String str, HashMap<String, Double> matched_words) {
        while(pq.peek() != null) {
            TrieNode temporary = pq.poll();
            traverseNode(temporary, str, matched_words);
            //bfs(temporary, str+temporary.c, matched_words);
        }
    }

    private TrieNode getVisitedChildNode(TrieNode node) {
        if(node.mid != null && node.mid.left != null &&  node.mid.left.visited) return node.left;
        if(node.mid != null && node.mid.right != null &&  node.mid.right.visited) return node.right;
        if(node.mid != null && node.mid.visited) return node.mid;
        return null;
    }

    private TrieNode getUnvisitedChildNode(TrieNode node) {
        if(node.mid != null && node.mid.left != null && !node.mid.left.visited) return node.mid.left;
        if(node.mid != null && node.mid.right != null && !node.mid.right.visited) return node.mid.right;
        if(node.mid != null && !node.mid.visited) return node.mid;
        return null;
    }

    private void traverseNode(TrieNode node, String prefix, HashMap<String, Double> matched_words) {
        TrieNode temp = node;
        TrieNode temporary = root;
        String str = prefix;
        for(int i = 0; i < prefix.length()  && temp != null && temporary != null; i++){
            char ch = prefix.charAt(i);

            //System.out.println(temp.c);
            if(ch < temporary.c) {
                i -= 1;
                temporary = temporary.left;
            }
            else if(ch > temporary.c) {
                i -= 1;
                temporary = temporary.right;
            }
            else if(i < prefix.length() - 1) {
                temporary = temporary.mid;
            }
        }

        while(temp != null) {
            str = str + temp.c;
            temp.visited = true;
            if(temp.weight != 0.0){
                matched_words.put(str, temp.weight);
            }
            temp = temp.mid;
            //str = str + temp.c;
        }
    }

    public boolean find(String word) {
        if (word == null || word.isEmpty()) return false;
        return find(root, word, 0);
    }

    public boolean find(TrieNode node, String word, int index) {
        if(node == null) return false;
        char ch = word.charAt(index);
        if(ch < node.c) return  find(node.left, word, index);
        if(ch > node.c) return  find(node.right, word, index);
        if(index < word.length() - 1) return  find(node.mid, word, index+1);
        return node.end;
    }

    public void insert(String word, double weight) {
        if(word != null && !word.isEmpty()) insert(root, word, 0, weight);
    }

    public TrieNode insert(TrieNode node, String word, int index, double weight) {
        char ch = word.charAt(index);
        if(node == null) {
           node = new TrieNode(ch);
           if(node.max_weight < weight) node.max_weight = weight;
        }

        if(ch < node.c){
             node.left =  insert(node.left, word, index, weight);
             //node.left.max_weight = weight;
         }
        else if(ch > node.c) {
             node.right =  insert(node.right, word, index, weight);
             //node.right.max_weight = weight;
         }
        else if(index < word.length() - 1) {
             node.mid = insert(node.mid, word, index+1, weight);
             //node.mid.max_weight = weight;
         }
        else {
            node.end = true;
            node.weight = weight;
            //node.max_weight = weight;
        }
        return node;
    }
    public TernaryTrie() {
        this.root = new TrieNode();
    }

}
