import java.util.*;
import org.w3c.dom.css.Counter;

//Customer class
class Customer {
    int id;
    int arrive_time;
    int order;
    int state;
    Counter_k counter;
    int wait_time;

    public Customer(int idd, int timeofarrival,Counter_k count, int burgers, int statee, int waittime){
        this.id = idd;
        this.counter = count;
        this.arrive_time = timeofarrival;
        this.order = burgers;
        this.state = statee;
        this.wait_time = waittime;
    }
}

//A class replicating the griddle with 2 queues, On cook burgers and On wait Burgers
class griddle {
    int max_burgers;
    int current_burgers;
    int on_wait_burgers;
    Queue<Customer> on_cook;
    Queue<Customer> wait_cook;

    public griddle(int m) {
        on_cook = new LinkedList<Customer>();
        wait_cook = new LinkedList<Customer>();
        this.max_burgers = m;
        this.current_burgers = 0;
    }

    public int current() {
        return (this.current_burgers);
    }

    public boolean isEmpty() {
        if (this.current_burgers < this.max_burgers) {
            return (true);
        }
        return (false);
    }

    public int space() {
        return (this.max_burgers - this.current_burgers);
    }
}

//Global_Clock
class global_clock {
    int time;

    public global_clock(int t) {
        this.time = t;
    }

    public void increment_time(int i) {
        this.time += i;
    }
}

//Class of Events in the Restaurant
class Events {
    int time;
    int prior;
    String what;
    Customer customl;
    Counter_k counter;

    public Events(String s, int t, Customer cust, Counter_k count) {
        this.what = s;
        this.counter = count;
        this.customl = cust;
        if (s == "arrival") {
            this.time = t;
            this.prior = 2;
        } else if (s == "send_to_chef") {
            this.time = t;
            this.prior = 6;
        } else if (s == "burger_remove") {
            this.time = t;
            this.prior = 4;
        } else if (s == "burger_add") {
            this.time = t;
            this.prior = 3;
        } else if (s == "deliver") {
            this.time = t;
            this.prior = 1;
        }
    }
}

//Class of Counters
class Counter_k {
    public Queue<Customer> queue;
    int id;
    int customs;

    public Counter_k(int i) {
        queue = new LinkedList<>();
        this.id = i;
        this.customs = queue.size();
    }

    public int length(){
        return (queue.size());
    }

    public void deque_(Customer i) {
        queue.remove(i);
    }

    public void enqueue_(Customer i) {
        queue.add(i);
    }

    public Customer top() {
        return (queue.peek());
    }

    public boolean isFree() {
        if (queue.size() == 1) {
            return (true);
        }
        return (false);
    }

    public int size(){
        return (queue.size());
    }

    // public void print_all(){
    //     for (int i =0;i<size();i++){
    //         System.out.println(queue.element());
    //     }
    // }
}

//Min-Heap for counters ; priority -> length -> id
class priority_queue_counters {
    public ArrayList<Counter_k> heap;
    int root_index = 1;

    public priority_queue_counters() {
        heap = new ArrayList<Counter_k>();
        Counter_k counter = new Counter_k(-1);
        counter.customs = -1;
        counter.id = -1;
        heap.add(0, counter);
    }

    public int number_of_nodes() {
        int size = heap.size();
        return (size - 1);
    }

    public Counter_k get_root_node() {
        int root = this.root_index;
        Counter_k elem = heap.get(root);
        return (elem);
    }

    // get parent for child with index i
    public Counter_k get_parent(int i) {
        Counter_k elem = heap.get(i / 2);
        return (elem);
    }

    // get left child of parent with index i
    public Counter_k get_left_child(int i) {
        Counter_k elem = heap.get(2 * i);
        return (elem);
    }

    // get right child of parent with index i
    public Counter_k get_right_child(int i) {
        Counter_k elem = heap.get(2 * i + 1);
        return (elem);
    }

    // find minimum which is the root node
    public Counter_k find_min() {
        int n = number_of_nodes();
        if (n == 0) {
            return (heap.get(-1));
        }
        return (this.get_root_node());
    }

    public void swap_pd(int i, Counter_k x) {
        Counter_k val_at_2i = this.get_left_child(i);
        Counter_k val_at_i = heap.get(i);
        Counter_k k = heap.set(i, val_at_2i);
        Counter_k m = heap.set(2 * i, x);
    }

    public void percolate_down(int i, Counter_k x) {
        int n = this.number_of_nodes();
        // no children
        if ((2 * i) > n) {
            Counter_k k = heap.set(i, x);
        }

        // 1 child
        else if ((2 * i) == n) {
            if (this.compare(x,this.get_left_child(i))) {
                swap_pd(i, x);
            } else {
                Counter_k k = heap.set(i, x);
            }
        }

        // 2 children
        else {
            int j;
            if (this.compare(this.get_right_child(i),this.get_left_child(i))) {
                j = 2 * i;
            } else {
                j = 2 * i + 1;
            }

            Counter_k elem = heap.get(j);

            if (this.compare(x,elem)) {
                Counter_k p = heap.set(i, elem);
                percolate_down(j, x);
            } else {
                Counter_k p = heap.set(i, x);
            }
        }
    }

    public boolean compare(Counter_k a, Counter_k b) {
        if (a.length() > b.length()) {
            return (true);
        } else if (a.length() < b.length()) {
            return (false);
        } else {
            if (a.id > b.id) {
                return (true);
            } else {
                return (false);
            }
        }
    }

    public void swap_with_parent(int i) {
        Counter_k elem = this.get_parent(i);
        Counter_k child = heap.get(i);
        heap.set(i, elem);
        heap.set(i / 2, child);
    }

    public void percolate_up(Counter_k x) {
        int i = heap.size();
        while (this.compare(this.get_parent(i),heap.get(i)) && i!=1 ){
            swap_with_parent(i);
            i = i / 2;
        }
    }

    public void build_heap() {
        int n = this.number_of_nodes();
        for (int i = n / 2; i > 0; i--) {
            percolate_down(i, heap.get(i));
        }
    }

    public Counter_k delete_min() {
        int n = this.number_of_nodes();
        Counter_k x = this.find_min();
        if (n == 1) {
            heap.remove(n);
        } else {
            Counter_k elem = heap.get(n);
            // System.out.println(elem);
            heap.remove(n);
            this.percolate_down(1, elem);
        }
        return (x);
    }

    public void insert(Counter_k i) {
        int n = heap.size();
        // System.out.println(n);
        int l = n;
        heap.add(l, i);
        // System.out.println(heap.size());
        this.percolate_up(i);
    }
}

//Min-Heap for events; priority
class priority_queue_events {
    public ArrayList<Events> heap;
    int root_index = 1;

    public priority_queue_events() {
        heap = new ArrayList<Events>();
        Counter_k counteri = new Counter_k(-1);
        Customer cust = new Customer(-1, -1,counteri, -1,-1, -1);
        Events counter = new Events("none", -1, cust, counteri);
        heap.add(0, counter);
    }

    public int number_of_nodes() {
        int size = heap.size();
        return (size - 1);
    }

    public Events get_root_node() {
        int root = this.root_index;
        Events elem = heap.get(root);
        return (elem);
    }

    // get parent for child with index i
    public Events get_parent(int i) {
        Events elem = heap.get(i / 2);
        return (elem);
    }

    // get left child of parent with index i
    public Events get_left_child(int i) {
        Events elem = heap.get(2 * i);
        return (elem);
    }

    // get right child of parent with index i
    public Events get_right_child(int i) {
        Events elem = heap.get(2 * i + 1);
        return (elem);
    }

    // find minimum which is the root node
    public Events find_min() {
        int n = number_of_nodes();
        if (n == 0) {
            return (heap.get(0));
        }
        return (this.get_root_node());
    }

    public void swap_pd(int i, Events x) {
        Events val_at_2i = this.get_left_child(i);
        Events val_at_i = heap.get(i);
        Events k = heap.set(i, val_at_2i);
        Events m = heap.set(2 * i, x);
    }

    public void percolate_down(int i, Events x) {
        int n = this.number_of_nodes();
        // no children
        if ((2 * i) > n) {
            Events k = heap.set(i, x);
        }

        // 1 child
        else if ((2 * i) == n) {
            if (this.compare(x,this.get_left_child(i))) {
                swap_pd(i, x);
            } else {
                Events k = heap.set(i, x);
            }
        }

        // 2 children
        else {
            int j;
            if (this.compare(this.get_right_child(i),this.get_left_child(i))) {
                j = 2 * i;
            } else {
                j = 2 * i + 1;
            }

            Events elem = heap.get(j);

            if (this.compare(x, elem)) {
                Events p = heap.set(i, elem);
                percolate_down(j, x);
            } else {
                Events p = heap.set(i, x);
            }
        }
    }

    public boolean compare(Events a, Events b) {
        if (a.time > b.time) {
            return (true);
        } else if (a.time < b.time) {
            return (false);
        } else {
            if (a.what!="send_to_chef"){
                if (a.prior > b.prior) {
                    return (true);
                } else {
                    return (false);
                }
            }
            else{
                if (a.prior > b.prior) {
                    return (true);
                } else if (a.prior < b.prior) {
                    return (false);
                }
                else{
                    if (a.counter.id<b.counter.id){
                        return (true);
                    }
                    else{
                        return (false);
                    }
                }
            }
        }
    }

    public void swap_with_parent(int i) {
        Events elem = this.get_parent(i);
        Events child = heap.get(i);
        heap.set(i, elem);
        heap.set(i / 2, child);
    }

    public void percolate_up(Events x) {
        int n = this.number_of_nodes()+1;
        // System.out.println(n);
        int i = n;
        heap.add(i, x);
        while (this.compare(this.get_parent(i),heap.get(i)) && i!=1) {
            swap_with_parent(i);
            i = i / 2;
        }
    }

    public void build_heap() {
        int n = this.number_of_nodes()+1;
        for (int i = n / 2; i >= 0; i--) {
            percolate_down(i, heap.get(i));
        }
    }

    public Events delete_min() {
        //System.out.println(heap.get(1).customl.id);
        //System.out.println(heap);
        Events x = this.find_min();
        int n = this.number_of_nodes();
        if (heap.size() ==2) {
            heap.remove(1);
        }
        else {
            Events elem = heap.get(n);
            //System.out.println("delete min");
            //System.out.println(elem.what);
            //System.out.println(elem.customl.id);
            
            heap.remove(n);
            //System.out.println(heap);
            this.percolate_down(1, elem); 
        }
        // System.out.println(heap.get(1).what);
        // System.out.println(heap.get(1).customl.id);
        // System.out.println(heap.get(1).time);
        // System.out.println(heap.get(2).what);
        // System.out.println(heap.get(2).customl.id);
        // System.out.println(heap.get(2).time);
        // System.out.println(heap.get(3).what);
        // System.out.println(heap.get(3).customl.id);
        // System.out.println(heap.get(3).time);

        //System.out.println(heap);
        return (x);
    }

    public void insert(Events i) {
        // System.out.println(heap.size());
        this.percolate_up(i);
    }
}

//Node class for AVL tree to search customer
class Node_AVL {
    public int key;
    public int timeofarrival;
    public Counter_k counter;
    public int burgers;
    public int statee;
    public int waittime;
    public int balance;
    public int height;
    public Node_AVL left, right, parent;

    public Node_AVL(int id, int toa,Counter_k count, int burg, int w, int st, Node_AVL p) {
        key = id;
        // this.parent = p;
        counter = count;
        timeofarrival = toa;
        burgers = burg;
        statee = st;
        waittime = w;
        parent = p;
    }
}

//AVL Class
class AVL {

    public Node_AVL root;

    public boolean insert(int key, int toa,Counter_k count, int burg, int w, int st) {
        if (root == null)
            root = new Node_AVL(key, toa,count, burg, w, st, null);
        else {
            Node_AVL n = root;
            Node_AVL parent;
            while (true) {
                if (n.key == key)
                    return false;

                parent = n;

                boolean goLeft = n.key > key;
                n = goLeft ? n.left : n.right;

                if (n == null) {
                    if (goLeft) {
                        parent.left = new Node_AVL(key, toa,count, burg, w, st, parent);
                    } else {
                        parent.right = new Node_AVL(key, toa,count, burg, w, st, parent);
                    }
                    rebalance(parent);
                    break;
                }
            }
        }
        return true;
    }

    public void delete(Node_AVL node) {
        if (node.left == null && node.right == null) {
            if (node.parent == null)
                root = null;
            else {
                Node_AVL parent = node.parent;
                if (parent.left == node) {
                    parent.left = null;
                } else
                    parent.right = null;
                rebalance(parent);
            }
            return;
        }
        if (node.left != null) {
            Node_AVL child = node.left;
            while (child.right != null)
                child = child.right;
            node.key = child.key;
            delete(child);
        } else {
            Node_AVL child = node.right;
            while (child.left != null)
                child = child.left;
            node.key = child.key;
            delete(child);
        }
    }

    public void delete(int delKey) {
        if (root == null)
            return;
        Node_AVL node = root;
        Node_AVL child = root;

        while (child != null) {
            node = child;
            child = delKey >= node.key ? node.right : node.left;
            if (delKey == node.key) {
                delete(node);
                return;
            }
        }
    }

    public void rebalance(Node_AVL n) {
        setBalance(n);

        if (n.balance == -2) {
            if (height(n.left.left) >= height(n.left.right))
                n = rotateRight(n);
            else
                n = rotateLeftThenRight(n);

        } else if (n.balance == 2) {
            if (height(n.right.right) >= height(n.right.left))
                n = rotateLeft(n);
            else
                n = rotateRightThenLeft(n);
        }

        if (n.parent != null) {
            rebalance(n.parent);
        } else {
            root = n;
        }
    }

    public Node_AVL rotateLeft(Node_AVL a) {

        Node_AVL b = a.right;
        b.parent = a.parent;

        a.right = b.left;

        if (a.right != null)
            a.right.parent = a;

        b.left = a;
        a.parent = b;

        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }

        setBalance(a, b);

        return b;
    }

    public Node_AVL rotateRight(Node_AVL a) {

        Node_AVL b = a.left;
        b.parent = a.parent;

        a.left = b.right;

        if (a.left != null)
            a.left.parent = a;

        b.right = a;
        a.parent = b;

        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }

        setBalance(a, b);

        return b;
    }

    public Node_AVL rotateLeftThenRight(Node_AVL n) {
        n.left = rotateLeft(n.left);
        return rotateRight(n);
    }

    public Node_AVL rotateRightThenLeft(Node_AVL n) {
        n.right = rotateRight(n.right);
        return rotateLeft(n);
    }

    public int height(Node_AVL n) {
        if (n == null)
            return -1;
        return n.height;
    }

    public void setBalance(Node_AVL... nodes) {
        for (Node_AVL n : nodes) {
            reheight(n);
            n.balance = height(n.right) - height(n.left);
        }
    }

    public void printBalance() {
        printBalance(root);
    }

    public void printBalance(Node_AVL n) {
        if (n != null) {
            printBalance(n.left);
            System.out.printf("%s ", n.balance);
            printBalance(n.right);
        }
    }

    public void reheight(Node_AVL node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }

    // public Node create_null_node(){
    // Node root = new Node(null,toa,burg,w,st, null);
    // return root;
    // }

    public Node_AVL search(int key) {
        Node_AVL result = searchHelper(this.root, key);
        if (result != null) {
            return result;
        }
        return null;
    }

    public Node_AVL searchHelper(Node_AVL root, int key) {
        // root is null or key is present at root
        if (root == null || root.key == key)
            return root;

        // key is greater than root's key
        if (root.key > key)
            return searchHelper(root.left, key); // call the function on the node's left child

        // key is less than root's key then
        // call the function on the node's right child as it is greater
        return searchHelper(root.right, key);
    }
}

public class MMBurgers implements MMBurgersInterface {
    int count;
    int max_burgers;
    int total_time;
    int total_customers;
    priority_queue_counters counters;
    priority_queue_events events = new priority_queue_events();
    griddle grid;
    global_clock system_time = new global_clock(0);
    AVL customers = new AVL();
    Stack all_customers = new Stack();

    public boolean isEmpty() {
        if (events.number_of_nodes() == 0) {
            //System.out.println(events.heap);
            //System.out.println("True");
            return (true);
        }
        
        else{
            //System.out.println("False");
            //System.out.println(events.heap.get(1).customl.order);
            return (false);
        }
        // your implementation
        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public void setK(int k) throws IllegalNumberException {
        // your implementation
        if (k>0){
            this.count = k;
            // create a heap for counters to find counter with minimum length
            counters = new priority_queue_counters();
            Counter_k count_k;
            for (int i = 0; i < k; i++) {
                count_k = new Counter_k(i + 1);
                counters.insert(count_k);
            }
        }
        else{
            throw new IllegalNumberException("Only positive integered queues allowed");
        }
        //System.out.println(counters.heap.get(1));
        //System.out.println(counters.heap.get(2));
        //System.out.println(counters.heap.get(3));

        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public void setM(int m) throws IllegalNumberException {
        // your implementation
        if (m>0){
            this.max_burgers = m;
            grid = new griddle(m);
        }
        else{
            throw new IllegalNumberException("Only positive integered burgers on griddle allowed");
        }
        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public void advanceTime(int t) throws IllegalNumberException {
        // your implementation
        if (t>=this.system_time.time){
            int nam = 0;
            boolean flag = true;
            while (events.find_min().time <= t && flag==true) {
                //System.out.println(nam);
                //System.out.println(events.heap);
                nam++;
                Events current = events.find_min();
                Customer current_customer = current.customl;
                Counter_k current_counter = current.counter;
                if (events.heap.size()>1){
                    events.delete_min();
                }
                else{
                    flag = false;
                }
                if (current.what == "arrival") {
                    current_customer.wait_time = current.time;
                    if (this.customers.search(current_customer.id) == null) {
                        this.customers.insert(current_customer.id,current_customer.arrive_time,current_customer.counter, current_customer.order, current_customer.wait_time,current_customer.state);
                        //Node_AVL customer_in_tree = this.customers.search(current_customer.id);
                        //System.out.println(root);
                    }

                    Node_AVL customer_in_tree = this.customers.search(current_customer.id);
                    //System.out.println(customer_in_tree);
                    customer_in_tree.waittime = t;
                    customer_in_tree.statee = current_counter.id;
                    if (current_counter.isFree()) {
                        this.place_order(current_customer, current_counter);
                    }
                } else if (current.what == "send_to_chef" && current.customl.order>0) {
                    this.departure(current_customer, current_counter);
                    Node_AVL customer_in_tree = this.customers.search(current_customer.id);
                    customer_in_tree.waittime = t;
                    customer_in_tree.statee = (count + 1);
                    if (this.grid.isEmpty()) {
                        if (current_customer.order < this.grid.space()) {
                            Events i = new Events("burger_add", current.time, current_customer, current_counter);
                            events.insert(i);
                        } else {
                            int new_order = current_customer.order - this.grid.space();
                            Customer dup_custom = new Customer(current_customer.id, current_customer.arrive_time,current_customer.counter, new_order,
                                    current_customer.state, current_customer.wait_time);
                            grid.on_wait_burgers+=dup_custom.order;
                            grid.wait_cook.add(dup_custom);
                            //System.out.println(grid.wait_cook);
                            current_customer.order = this.grid.space();
                            Events i = new Events("burger_add", current.time, current_customer, current_counter);
                            events.insert(i);
                        }
                    } else {
                        grid.on_wait_burgers+=current_customer.order;
                        grid.wait_cook.add(current_customer);
                    }
                } else if (current.what == "burger_remove" && current.customl.order>0){
                    int vacancy = current_customer.order;
                    grid.on_cook.remove(current_customer);
                    //System.out.println("just after removing");
                    //System.out.println(grid.on_cook.peek().order);
                    grid.current_burgers = grid.current_burgers-current_customer.order;
                    //int vacancy = 0;
                    int grid_clone = vacancy;
                    boolean flag2 = true;
                    while (grid_clone>0 && flag2 == true){
                        //System.out.println(grid.wait_cook);
                        if (grid.wait_cook.size()!=0){
                        //System.out.println("executed");
                            Customer new_cust = grid.wait_cook.peek();
                            //grid_clone = grid_clone - new_cust.order;
                            if (grid_clone>=new_cust.order){
                                grid_clone-=new_cust.order;
                                grid.on_wait_burgers=grid.on_wait_burgers-new_cust.order;
                                Events i = new Events("burger_add", current.time, new_cust,new_cust.counter);
                                events.insert(i);
                                grid.wait_cook.remove();
                            }
                            else{
                                Customer new_cust_1 = new Customer(new_cust.id,new_cust.arrive_time,new_cust.counter,grid_clone,new_cust.state,new_cust.wait_time);
                                grid.wait_cook.peek().order = grid.wait_cook.peek().order - grid_clone;
                                // System.out.println("check1");
                                // System.out.println(new_cust.order);
                                // System.out.println("check1");
                                // System.out.println(new_cust.order - grid_clone);
                                Events i = new Events("burger_add", current.time, new_cust_1,new_cust_1.counter);
                                events.insert(i);
                                grid.on_wait_burgers=grid.on_wait_burgers-grid_clone;
                                grid_clone = 0;
                            }  
                        }
                        else{
                            flag2 = false;
                        }
                    // System.out.println("after adding wait");
                        //System.out.println(grid.wait_cook.peek().order);
                    }
                    Events i = new Events("deliver", current.time + 1, current_customer, current_counter);
                    events.insert(i);
                } else if (current.what == "burger_add" && current.customl.order>0) {
                    grid.on_cook.add(current_customer);
                    grid.current_burgers+=current_customer.order;
                    Events i = new Events("burger_remove", current.time + 10, current_customer, current_counter);
                    events.insert(i);
                } else if (current.what == "deliver" && current.customl.order>0) {
                    Node_AVL customer_in_tree = this.customers.search(current_customer.id);
                    customer_in_tree.waittime = current.time ;
                    customer_in_tree.statee = (count + 2);
                }
                this.system_time.time = current.time;
                // System.out.print(current.what);
                // System.out.print(current.time);
                // System.out.print(current.customl.id);
                // System.out.print(current.customl.order);
                // System.out.println("\n");
                // System.out.println(events.heap.get(1).what);
                // System.out.println(events.heap.get(1).customl.id);
                // System.out.println(events.heap.get(1).time);
                // System.out.println(events.heap.get(2).what);
                // System.out.println(events.heap.get(2).customl.id);
                // System.out.println(events.heap.get(2).time);
                // System.out.println(events.heap.get(3).what);
                // System.out.println(events.heap.get(3).customl.id);
                // System.out.println(events.heap.get(3).time);
                // System.out.println(events.heap.get(4).what);
                // System.out.println(events.heap.get(4).customl.id);
                // System.out.println(events.heap.get(4).time);
                // System.out.println("minimum");
                // System.out.println(current);
                // System.out.println(current.what);
                // System.out.println(current.customl.id);
                // System.out.println(current.time);
                // System.out.println("just before");
                // System.out.println(events.heap);
                // events.delete_min();
                // System.out.println(events.heap);
                // System.out.println(events.heap.get(1).what);
                // System.out.println(events.heap.get(1).customl.id);
                // System.out.println(events.heap.get(1).time);
                // System.out.println(events.heap.get(2).what);
                // System.out.println(events.heap.get(2).customl.id);
                // System.out.println(events.heap.get(2).time);
                // System.out.println(events.heap.get(3).what);
                // System.out.println(events.heap.get(3).customl.id);
                // System.out.println(events.heap.get(3).time);
                // System.out.println(events.heap.get(4).what);
                // System.out.println(events.heap.get(4).customl.id);
                // System.out.println(events.heap.get(4).time);
            }
            // System.out.println("check");
            // System.out.println(events.heap.get(1).what);
            // System.out.println(events.heap.get(1).customl.id);
            // System.out.println(events.heap.get(1).time);
            // System.out.println(events.heap.get(2).what);
            // System.out.println(events.heap.get(2).customl.id);
            // System.out.println(events.heap.get(2).time);
            this.system_time.time = t;
        }
        else{
            throw new IllegalNumberException("cannot simulate time backwards");
        }
        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public void place_order(Customer i, Counter_k k) {
        i.wait_time = this.system_time.time + k.id;
        //System.out.println("check wait");
        //System.out.println(i.wait_time);
        Events new_event = new Events("send_to_chef", i.wait_time, i, k);
        this.events.insert(new_event);
    }

    public void departure(Customer i, Counter_k k) {
        i.state = this.count + 1;
        i.wait_time = this.system_time.time;
        //i.counter = -1;
        k.deque_(i);
        this.counters.heap.get(k.id).deque_(i);
        //System.out.println("before");
        //System.out.println(this.counters.find_min().id);
        this.counters.heap.get(k.id).customs = this.counters.heap.get(k.id).customs-1;
        this.counters.build_heap();
        //System.out.println("after");
        //System.out.println(this.counters.find_min().id);
        //System.out.println("size check");
        //System.out.println(k.size());
        if (k.size() != 0) {
            place_order(k.top(), k);
        }
    }

    // public void add_to_griddle(Customer i){

    public void arriveCustomer(int id, int t, int numb) throws IllegalNumberException {
        // your implementation
        if (t>=this.system_time.time && numb>0){
            this.advanceTime(t);
            this.all_customers.push(id);
            total_customers+=1;
            Counter_k chosen = (Counter_k) this.counters.find_min();
            this.counters.delete_min();
            chosen.customs += 1;
            Customer guest = new Customer(id, t,chosen, numb, chosen.id, t);
            guest.counter = chosen;
            chosen.enqueue_(guest);
            this.counters.insert(chosen);
            // System.out.println("counter check");
            // System.out.println(chosen.id);
            // System.out.println("customer check");
            // System.out.println(id);
            Events new_event = new Events("arrival", t, guest, chosen);
            this.events.insert(new_event);
        }
        else if (t<this.system_time.time){
            throw new IllegalNumberException("Time cannot be lower than the system time");
        }

        else{
            throw new IllegalNumberException("Order must be positive");
        }
        // if (chosen.top().id == guest.id){
        // //place order
        // this.place_order(guest, chosen);
        // }
        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public int customerState(int id, int t) throws IllegalNumberException {
        // your implementation
        if (t>=this.system_time.time){
            this.advanceTime(t);
            if (this.customers.search(id) != null) {
                Node_AVL found = this.customers.search(id);
                // Customer custom = new Customer(found.key, found.timeofarrival, found.burgers,
                // found.statee, found.waittime);
                //System.out.println(found.statee);
                return (found.statee);
            }
            else{
                //System.out.println(0);
                return (0);
            }
        }
        else{
            throw new IllegalNumberException("Time cannot be lower than the system time");
        }
        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public int griddleState(int t) throws IllegalNumberException {
        // your implementation
        if (t>=this.system_time.time){
            this.advanceTime(t);
            int n = this.grid.current();
            //System.out.println(n);
            return (n);
        }
        else{
            throw new IllegalNumberException("Time cannot be lower than the system time");
        }
        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public int griddleWait(int t) throws IllegalNumberException {
        // your implementation
        if (t>=this.system_time.time){
            this.advanceTime(t);
            int res = this.grid.on_wait_burgers;
            //System.out.println(res);
            return (res);
        }
        else{
            throw new IllegalNumberException("Time cannot be lower than the system time");
        }
        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public int customerWaitTime(int id) throws IllegalNumberException {
        // your implementation
        if (this.customers.search( id) != null) {
            Node_AVL found = this.customers.search(id);
            // Customer custom = new Customer(found.key, found.timeofarrival, found.burgers,
            // found.statee, found.waittime);
            //System.out.println(found.waittime);
            return (found.waittime-found.timeofarrival);
        }
        else{
            //System.out.println(0);
            throw new IllegalNumberException("customer did not arrive yet");
        }
        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public float avgWaitTime() {
        // your implementation
        for (int i = 0; i < total_customers; i++) {
            Node_AVL found = this.customers.search((int) this.all_customers.pop());
            total_time += (found.waittime-found.timeofarrival);
        }
        //System.out.println((float)total_time / total_customers);
        return ((float)total_time / total_customers);
        // throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

}