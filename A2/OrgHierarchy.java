import java.io.*; 
import java.util.*;
import java.lang.Math;

// Tree node
class Node_AVL{
	int key;
	int level;
	int boss;
	Vector<Integer> employee = new Vector<Integer>(); 
	int height;
	int desc;
	Node_AVL left, right;

	Node_AVL(int k, int l, int p, Vector j){
		this.key = k;
		this.level = l;
		this.boss = p;
		this.employee = j;
		this.height = 0;
		this.left = null;
		this.right = null;
		this.desc = 0;
	}
}

class AVL{

	public Node_AVL root;
	public int size=0;

	public AVL(){
		root = null;
	}

	public int get_height(Node_AVL x){
		if (x == null){
			return -1;
		}
		return (x.height);
	}

	public int max_func(int a, int b){
		if (a>b){
			return a;
		}
		return b;
	}

	public int recompute_height(Node_AVL x){
		x.height = 1+max_func(get_height(x.left), get_height(x.right));
		return (x.height);
	}

	public boolean isBalanced(Node_AVL x){
		if (x==null){
			return true;
		}
		if (Math.abs(get_height(x.left)-get_height(x.right))<=1){
			return true;
		}
		return false;
	}

	public int getbalance(Node_AVL x){
		if (x==null){
			return 0;
		}
		else{
			return (get_height(x.left)-get_height(x.right));
		}
	}

	public Node_AVL Right_Rotate(Node_AVL y){
		Node_AVL x = y.left;
		Node_AVL child_x = x.right;
        x.right = y;
        y.left = child_x;

		y.height = recompute_height(y);
		x.height = recompute_height(x);

		return (x);
	}

	public Node_AVL Left_Rotate(Node_AVL y){
        Node_AVL x = y.right;
        Node_AVL child_x = x.left;

        x.left = y;
        y.right = child_x;
		y.height = recompute_height(y);
		x.height = recompute_height(x);

		return (x);
	}

	public Node_AVL leftleft(Node_AVL x){
		return Right_Rotate(x);
	}

	public Node_AVL leftright(Node_AVL x){
		x.left=Left_Rotate(x.left);
		return Right_Rotate(x);
	}

	public Node_AVL rightright(Node_AVL x){
		return Left_Rotate(x);
	}

	public Node_AVL rightleft(Node_AVL x){
		x.right=Right_Rotate(x.right);
		return Left_Rotate(x);
	}

	public Node_AVL balance_tree(Node_AVL x){
		int balance = getbalance(x);
        //System.out.println(balance);
		if (balance>1){
			if (getbalance(x.left)>=0){
				return (leftleft(x));
                //System.out.println("hi left left");
				
			}
			else{
                //System.out.println("hi left right");
				return (leftright(x));
			}
		}
		else if (balance <-1){
			if (getbalance(x.right)<=0){
                //System.out.println("hi right right");
				return (rightright(x));
			}
			else{
                //System.out.println("hi right left");
				return (rightleft(x));
			}

		}
		return (x);
	}

	

	public Node_AVL insert(Node_AVL root, int k,Node_AVL node){
        //System.out.println(k);
		if (root == null){
			return (node);
		}
		if (k<root.key){
			root.left = insert(root.left, k, node);
		}
		else{
			root.right = insert(root.right,k,node);
		}
		root.height = recompute_height(root);
        //System.out.println(root.height);
		root = balance_tree(root);
        return (root);
	}

	public Node_AVL insert_in_tree(Node_AVL root,int k, int l, int p, Vector j){
		Node_AVL node = new Node_AVL(k, l, p, j);
		return insert(root,node.key,node);
	}

    public Node_AVL get_predecessor(Node_AVL x){
		Node_AVL temp = x;
        while (temp.left!=null){
           temp = temp.left;
        }
        return (temp);
    }

    public void changeNode(Node_AVL x, Node_AVL y){
        int k = x.key;
        int b = x.boss;
        Vector e = x.employee;
        int l = x.level;
        y.key = k;
        y.boss = b;
        y.employee = e;
        y.level = l;
    }

	// public Node_AVL null_node(){
	// 	return (Node_AVL(null,null,null,null));
	// }


    public Node_AVL delete(Node_AVL root ,int key){
        if (root==null){
            return root;
        }

        else{
            if (key<root.key){
                root.left = delete(root.left, key);
            }

            else if (key>root.key){
                root.right = delete(root.right, key);
            }
            else{
                if ((root.right==null) || (root.left==null)){
					
                    if (root.right==null && root.left==null){
                        root = null;
                    }
                    else if (root.right==null){
                        root = root.left;
						root.left = null;
                    }
                    else {
                        root = root.right;
						root.right = null;
                    }
                }
                else{
                    Node_AVL val = get_predecessor(root.right);
                    changeNode(val, root);
                    root.right= delete(root.right, val.key);
                }
            }
            if (root==null){
                return (root);
            }
            root.height = recompute_height(root);
            root = balance_tree(root);
            return (root);
            }
    }

    public Node_AVL search(Node_AVL root,int key_search){
		if (root!=null){
			if (key_search==root.key){
				return (root);
			}
			else if (key_search>root.key){
				return (search(root.right, key_search));
			}
			else if (key_search<root.key){
				return (search(root.left, key_search));
			}
		}
		else{
			return (root);
		}
		return (root);
    }

	public Vector<Vector<Integer>> nodes_at_level = new Vector<Vector<Integer>>();

	public void add_node_at_level(Node_AVL x){
		int lev = x.level;
		if (nodes_at_level.size()>lev){
			nodes_at_level.get(lev).add(x.key);
		}
		else{
			nodes_at_level.insertElementAt(new Vector<>(), lev);
			nodes_at_level.get(lev).add(x.key);
		}
	}

	public void delete_node_at_level(Node_AVL x){
		//System.out.println(nodes_at_level);
		int lev = x.level;
		nodes_at_level.get(lev).removeElement(x.key);
	}
}


public class OrgHierarchy implements OrgHierarchyInterface{

//root node
	AVL tree = new AVL();
	int owner;

	public boolean isEmpty(){
		//your implementation
		try{
			if (tree.root == null){
				return (true);
			}
			return (false);
		}
		catch (Exception e){
			throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		}
	} 

	public int size(){
		//your implementation
		try {
			return tree.size;
		}
		catch (Exception e){
			throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		}
	}

	public int level(int id) throws IllegalIDException, EmptyTreeException{
		//your implementation
		if ((tree.search(tree.root,id)!=null) && tree.size!=0) {
			Node_AVL temp = tree.search(tree.root,id);
			return (temp.level+1);
		}
		else if (tree.size==0){
			throw new EmptyTreeException("Empty Tree");
		}
		else {
			throw new IllegalIDException("ID does not exist");
		}
		
	} 

	public void hireOwner(int id) throws NotEmptyException{
		//your implementation
			if (tree.size==0){
				owner = id;
				tree.size+=1;
				Vector<Integer> v = new Vector<Integer>();
				Node_AVL node = new Node_AVL(id, 0, -1, v);
				tree.add_node_at_level(node);
				tree.root = tree.insert_in_tree(tree.root, id, 0, -1, v);
			}
			else{
				throw new NotEmptyException("Owner Exists");
			}
		}

	public void hireEmployee(int id, int bossid) throws IllegalIDException, EmptyTreeException{
		//your implementation
		if ((tree.size!=0)&&(tree.search(tree.root, bossid)!=null)){
			tree.size+=1;
			Vector<Integer> v = new Vector<Integer>();
			Node_AVL temp = tree.search(tree.root, bossid);
			int l = temp.level+1;
			temp.employee.add(id);
			Node_AVL node = new Node_AVL(id, l, bossid, v);
			tree.add_node_at_level(node);
			tree.root = tree.insert_in_tree(tree.root, id, l, bossid, v);
		}
		else if (tree.size==0){
			throw new EmptyTreeException("Empty Tree");
		}
		else{
			throw new IllegalIDException("BossID Doesn't exists");
		}
	} 

	

	public void fireEmployee(int id) throws IllegalIDException,EmptyTreeException{
		//your implementation
		if ((tree.search(tree.root,id)!=null)&&(tree.size!=0)&&(id!=owner)){
			//System.out.println(tree.root.key);
			Node_AVL emp = tree.search(tree.root,id);
			Node_AVL temp = tree.search(tree.root, emp.boss);
			temp.employee.removeElement(id);
			tree.delete_node_at_level(emp);
			tree.root = tree.delete(tree.root, id);
			tree.size=tree.size-1;
		}
		else if (tree.size==0){
			throw new EmptyTreeException("Empty Tree");
		}
		else if (id==owner){
			throw new IllegalIDException("Cannot fire owner");
		}
		else{
			throw new IllegalIDException("ID does not exist");
		}

	}

	public int flag = 0;

	public boolean valid_fire(int id,int manageid){
		if (tree.search(tree.root,id)==null){
			return (false);
		}
		else if (tree.size==0){
			return (false);
		}
		else if (tree.search(tree.root,manageid)==null){
			return (false);
		}
		else if (id==owner){
			flag = 1;
			return (false);
		}
		else if (manageid==owner){
			return (false);
		}
		return (true);
	}

	public void fireEmployee(int id, int manageid) throws IllegalIDException,EmptyTreeException{
		//your implementation
		if (valid_fire(id,manageid)){
			Node_AVL emp = tree.search(tree.root,id);
			Node_AVL new_boss = tree.search(tree.root,manageid);
			Vector v = emp.employee;
			fireEmployee(id);
			for (int i=0;i<v.size();i++){
				Node_AVL temp = tree.search(tree.root, (int)v.get(i));
				temp.boss = manageid;
				new_boss.employee.add((int)v.get(i));
			}
		}
		else if (tree.size==0){
			throw new EmptyTreeException("Empty Tree");
		}
		else if (flag==1){
			throw new IllegalIDException("Cannot fire owner");
		}
		else{
			throw new IllegalIDException("ID Does not exist");
		}
	} 

	public int boss(int id) throws IllegalIDException,EmptyTreeException{
		//your implementation
		if (tree.search(tree.root, id)!=null && tree.size!=0){
			Node_AVL temp = tree.search(tree.root, id);
			return (temp.boss);
		}
		else if (tree.size==0){
			throw new EmptyTreeException("Empty Tree");
		}
		else{
			throw new IllegalIDException("ID Does not exist");
		}
	}

	public int lowestCommonBoss(int id1, int id2) throws IllegalIDException,EmptyTreeException{
		//your implementation
		if (tree.search(tree.root, id1)!=null && tree.search(tree.root, id2)!= null && tree.size!=0) {
			Node_AVL emp1 = tree.search(tree.root, id1);
			Node_AVL emp2 = tree.search(tree.root, id2);
			int level_1 = emp1.level;
			int level_2 = emp2.level;
			if (level_1>level_2){
				return (emp2.boss);
			}
			else{
				return (emp1.boss);
			}
		}
		else if (tree.size==0){
			throw new EmptyTreeException("Empty Tree");
		}
		else{
			throw new IllegalIDException("ID Doesn't exists");
		}
	}

	public String toString(int id) throws IllegalIDException, EmptyTreeException{
		//your implementation
		if (tree.search(tree.root, id)!=null && tree.size!=0){
			Vector<Vector<Integer>> descendants = new Vector<Vector<Integer>>();
			Node_AVL temp = tree.search(tree.root, id);
			int level = temp.level;
			Vector v = tree.nodes_at_level;
			Vector<Integer> bosses = new Vector<>();
			bosses.add(temp.key);
			//System.out.println(level);
			//System.out.println(v);
			//System.out.println(v);
			String fin = new String();
			fin += Integer.toString(id);
			int k=0;
			//System.out.println(v);
			for (int i = level+1;i<v.size();i++){
				for (int j = 0;j<(int)((Vector)v.get(i)).size();j++){
					int app = (int)((Vector)v.get(i)).get(j);
					Node_AVL kid = tree.search(tree.root, app);
					if (bosses.contains(kid.boss)){
						if (descendants.size()>k){
							descendants.get(k).add(app);
						}
						else{
							descendants.insertElementAt(new Vector<>(), k);
							descendants.get(k).add(app);
						}
						bosses.add(app);	
					}	
				}
				k+=1;
			}
			//System.out.println(descendants);
			for (int i = 0;i<descendants.size();i++){
				Collections.sort(descendants.get(i));
				for (int j = 0;j<(int)((Vector)descendants.get(i)).size();j++){
					if (i==0 && j==0){
						fin += ",";
					}
					int emp = (int)((Vector)descendants.get(i)).get(j);
					fin += Integer.toString(emp);
					
					if (j!=(int)((Vector)descendants.get(i)).size()-1){
						fin+=" ";
					}
				}
				if (i!=descendants.size()-1){
					fin+=",";
				}
			}
			return (fin);	
		}
		else if (tree.size==0){
			throw new EmptyTreeException("Empty Tree");
		}
		else{
			throw new IllegalIDException("ID doesn't exists");
		}
	}
}
