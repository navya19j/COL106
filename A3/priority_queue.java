import java.util.ArrayList;

public class priority_queue{
    public ArrayList<Integer> heap;
    int root_index = 1;
    
    public priority_queue(){
        heap = new ArrayList<Integer>();
        heap.add(0,-1);
    }

    public int number_of_nodes(){
        int size = heap.size();
        return (size-1);
    }

    public int get_root_node(){
        int root = this.root_index;
        int elem = heap.get(root);
        return (elem);
    }

    //get parent for child with index i
    public int get_parent(int i){
        int elem = heap.get(i/2);
        return (elem);
    }
    
    //get left child of parent with index i
    public int get_left_child(int i){
        int elem = heap.get(2*i);
        return (elem);
    }

    //get right child of parent with index i
    public int get_right_child(int i){
        int elem = heap.get(2*i+1);
        return (elem);
    }

    // find minimum which is the root node
    public int find_min(){
        int n = number_of_nodes();
        if (n==0){
            return (-1);
        }
        return (this.get_root_node());
    }

    public void swap_pd(int i,int x){
        int val_at_2i = this.get_left_child(i);
        int val_at_i = heap.get(i);
        int k = heap.set(i,val_at_2i);
        int m = heap.set(2*i,x);
    }

    public void percolate_down(int i, int x){
        int n = this.number_of_nodes();
        //no children
        if ((2*i)>n){
            int k = heap.set(i,x);
        }

        //1 child
        else if ((2*i)==n){
            if (this.get_left_child(i)<x){
                swap_pd(i, x);
            }
            else{
                int k = heap.set(i,x);
            }
        }

        //2 children
        else{
            int j;
            if (this.get_left_child(i)<this.get_right_child(i)){
                j = 2*i;
            }
            else{
                j = 2*i+1;
            }

            int elem = heap.get(j);

            if (elem<x){
                int p = heap.set(i,elem);
                percolate_down(j, x);
            }
            else{
                int p = heap.set(i,x);
            }
        }
    }

    public void swap_with_parent(int i){
        int elem = this.get_parent(i);
        int child = heap.get(i);
        heap.set(i,elem);
        heap.set(i/2,child);
    }

    public void percolate_up(int x){
        int n = heap.size();
        //System.out.println(n);
        int i = n;
        heap.add(i,x);
        while (heap.get(i)<this.get_parent(i) && i!=1){
            swap_with_parent(i);
            i = i/2;
        } 
    }

    public void build_heap(){
        int n = this.number_of_nodes()+1;
        for (int i = n/2;i>0;i--){
            percolate_down(i, heap.get(i));
        }
    }

    public int delete_min(){
        int n = this.number_of_nodes();
        int x = this.find_min();
        if (n==1){
            heap.remove(n);
        }
        else{
            int elem = heap.get(n);
            System.out.println("dele");
            System.out.println(elem);
            //System.out.println(heap);
            heap.remove(n);
            //System.out.println(heap);
            this.percolate_down(1, elem);
           
        }
        return (x);
    }

    public void insert(int i){
        //System.out.println(heap.size());
        this.percolate_up(i);
    }


public static void main(String[] arg)
    {
        System.out.println("The Min Heap is ");
        priority_queue minHeap = new priority_queue();
        //System.out.println(minHeap.heap);
        minHeap.insert(5);
        //System.out.println(minHeap.heap);
        minHeap.build_heap();
        //System.out.println(minHeap.heap);
        minHeap.insert(3);
        ///System.out.println(minHeap.heap);
        minHeap.build_heap();
        //System.out.println(minHeap.heap);
        minHeap.insert(17);
        //minHeap.insert(89);
        minHeap.heap.set(2,2);
        //System.out.println(minHeap.heap);
        System.out.println(minHeap.heap);
        minHeap.build_heap();
        System.out.println("final");
        System.out.println(minHeap.heap);
        minHeap.insert(10);
        //System.out.println(minHeap.heap);
        minHeap.build_heap();
        //System.out.println(minHeap.heap);
        minHeap.insert(84);
        //System.out.println(minHeap.heap);
        minHeap.build_heap();
        //System.out.println(minHeap.heap);
        minHeap.insert(19);
        //System.out.println(minHeap.heap);
        minHeap.build_heap();
        //System.out.println(minHeap.heap);
        minHeap.insert(6);
        //System.out.println(minHeap.heap);
        minHeap.build_heap();
        //System.out.println(minHeap.heap);
        minHeap.insert(22);
        //System.out.println(minHeap.heap);
        minHeap.build_heap();
        //System.out.println(minHeap.heap);
        minHeap.insert(9);
        //System.out.println(minHeap.heap);
        minHeap.build_heap();
        //System.out.println(minHeap.heap);
        minHeap.delete_min();
        //System.out.println(minHeap.heap);
        minHeap.build_heap();
        //System.out.println(minHeap.heap);
        minHeap.delete_min();
        //System.out.println(minHeap.heap);
        minHeap.build_heap();
        //System.out.println(minHeap.heap);
        minHeap.delete_min();
        //System.out.println(minHeap.heap);
        minHeap.build_heap();
        //System.out.println(minHeap.heap);
        minHeap.delete_min();
        //System.out.println(minHeap.heap);
        minHeap.build_heap();
        //System.out.println(minHeap.heap);
        minHeap.delete_min();
        //System.out.println(minHeap.heap);
        minHeap.build_heap();
        //System.out.println(minHeap.heap);
        minHeap.delete_min();
        //System.out.println(minHeap.heap);
        minHeap.build_heap();
        //System.out.println(minHeap.heap);
        minHeap.delete_min();
        //System.out.println(minHeap.heap);
        minHeap.build_heap();
        //System.out.println(minHeap.heap);
        minHeap.delete_min();
        //System.out.println(minHeap.heap);
        minHeap.build_heap();
        //System.out.println(minHeap.heap);
        //minHeap.delete_min();
        //System.out.println(minHeap.heap);
        minHeap.build_heap();
        //System.out.println(minHeap.heap);
        //minHeap.minHeap();
  
        //minHeap.print();
        System.out.println("The Min val is " + minHeap.find_min());
    }
}
