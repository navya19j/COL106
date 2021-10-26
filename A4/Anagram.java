import java.io.BufferedReader;
import java.io.File;
import java.util.*;

import java.io.PrintStream;
import javax.swing.border.StrokeBorder;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.ReadOnlyFileSystemException;
import java.io.FileWriter;

class HashNode {
    String key;
    Vector<String> value = new Vector<String>();
    

    public HashNode(String key, Vector value) {
        this.key = key;
        this.value = value;
    }
}

class HashMap {
    public ArrayList<HashNode> hash_table;
    public int capacity;
    public int collision;
    public ArrayList<String> three_letter = new ArrayList<String>();
    public ArrayList<String> four_letter= new ArrayList<String>();
    public ArrayList<String> five_letter= new ArrayList<String>();
    public ArrayList<String> six_letter= new ArrayList<String>();

    public HashMap(int capacity) {
        hash_table = new ArrayList<HashNode>(capacity);
        for (int i = 0; i < capacity; i++) {
            hash_table.add(null);
        }
    }

    public String get_key(String s) {
        String val;
        // bucket_sort bucket = new bucket_sort();
        char tempArray[] = s.toCharArray();
        Arrays.sort(tempArray);
        val = new String(tempArray);
        // val = bucket.sort(s);
        return (val);
    }

    public int hashfunc(String s) {
        String res = get_key(s);
        int len = res.length();
        int ind = 0;
        for (int i = 0; i < len; i++) {
            ind = ((int) res.charAt(i) + 256 * ind) % (capacity);
            // System.out.println(capacity);
        }
        return (ind);
    }
    
    public void add(String s) {
        if (s.length() < 13) {
            // key is the key that will be stored in the hash table
            String key = get_key(s);
            // val is the word from the vocab
            String val = s;
            int index = this.hashfunc(key);
            this.add_helper(key, val, index,0);

        }
    }

    public void add_helper(String key, String val, int index, int curr) {
        if (this.hash_table.get(index) == null){
            Vector key_hash = new Vector();
            key_hash.add(val);
            HashNode node = new HashNode(key, key_hash);
            this.hash_table.set(index, node);
        } else {
            HashNode node = this.hash_table.get(index);
            String temp = node.key.toString();
            if (temp.equals(key)) {
                node.value.add(val);
            } else {
                collision+=1;
                // collision resolution : linear probing for start
                this.add_helper(key, val, index + (int)Math.pow(curr+1,2),curr+1);
            }
        } 
    }

    public int find(String s) {
        String key = get_key(s);
        //System.out.println(key);
        boolean flag = true;
        int index = this.hashfunc(key);
        //System.out.println(index);
        HashNode node = this.hash_table.get(index);
        //System.out.println(node.key);
        //System.out.println(key);
        // System.out.println(index);
        int j = 0 ;
        while ((node != null) & (flag == true)) {
            if (node.key.equals(key)) {
                //System.out.println("exec1");
                flag = false;
                return (index);
            } else if (node.key != null) {
                //System.out.println("exec2");
                j+=1;
                index = (index + ((int)Math.pow(j, 2))%(capacity)) % (capacity);
                node = this.hash_table.get(index);   
            } else {
                //System.out.println("exec3");
                return (-1);
            }
        }
        return (-1);
    }
}

public class Anagram {

    static int capacity = (100003);
    static HashMap hash = new HashMap(capacity);
    static ArrayList six;
    static ArrayList five;
    static ArrayList four;
    static ArrayList three;

    public static  void set_capacity(int n){
        hash.capacity = n;
    }

    public static  void add_in_hash(String s){
        hash.add(s);
    }

    public static  ArrayList create_anag_1(String s){
        ArrayList<String> one_word = new ArrayList<String>();
        int index = hash.find(s);
        if (index!=-1){
            ArrayList table = hash.hash_table;
            HashNode anagram_node = (HashNode) table.get(index);
            Vector anagram = (anagram_node).value;
            for (int i = 0; i < anagram.size(); i++){
                one_word.add((String)anagram.get(i));
                //System.out.println(anagram.get(i));
            }
            Collections.sort(one_word);
        }
        return (one_word);
    }

    public static void create_partition(int i,int n,int k, int count, ArrayList<ArrayList<String>> ans, ArrayList<String> inp,ArrayList<ArrayList<String>> res){
        

        if (i>=n){
            if (count == k){
                //System.out.println(ans);
                boolean flag = true;
                for (int l =0 ; l<ans.size();l++){
                    ArrayList<String> curr = ans.get(l);
                    if (curr.size()<3){
                        flag = false;
                    }
                    //System.out.println(curr);
                }
                if (flag == true){
                    ArrayList to_add = new ArrayList<>();
                    for (int l =0 ; l<ans.size();l++){
                        ArrayList<String> curr = ans.get(l);
                        String fin = new String("");
                        for (int m=0;m<curr.size();m++){
                            fin+=(String)curr.get(m);
                        }
                        to_add.add(fin);
                        //System.out.println(curr);
                    }
                    res.add(to_add);
                }
            }
            return ;
        }

        for (int j=0;j<k;j++){
            if (ans.get(j).size()>0){
                ans.get(j).add((String)inp.get(i));
                create_partition(i+1, n, k, count, ans, inp,res);
                ans.get(j).remove(ans.get(j).size()-1);
            }
            else{
                ans.get(j).add((String)inp.get(i));
                create_partition(i+1, n, k, count+1, ans, inp,res);
                ans.get(j).remove(ans.get(j).size()-1);
                break;
            }
        }
    }

    public static ArrayList create_partition_main(String s,int k){
        ArrayList<ArrayList<String>> ans = new ArrayList<>();
        ArrayList<String> inp = new ArrayList<>();
        for (int i=0;i<k;i++){
            ans.add(new ArrayList<>());
        }
        for (int j=0;j<s.length();j++){
            char res = s.charAt(j);
            String res_new = String.valueOf(res);
            inp.add(res_new);
        }
        ArrayList<ArrayList<String>> res = new ArrayList<>();
        create_partition(0,s.length(),k,0, ans, inp,res);
        return(res);
    }

    public static void check_if_in_hash(ArrayList<ArrayList<String>> res,ArrayList<ArrayList<String>> fin){
        for (int i=0;i<res.size();i++){
            ArrayList temp = res.get(i);
            boolean flag = true;
            int m =0;
            while ((flag == true) & (m<temp.size())){
                String curr = (String)temp.get(m);
                int temp0 = hash.find(curr);
                m+=1;
                if (temp0 == -1){
                    flag = false;
                }
            }
            if (flag== true){
                ArrayList temp1 = new ArrayList<>();
                for (int k=0;k<temp.size();k++){
                    String curr = (String)temp.get(k);
                    String curr_1 = (String)hash.get_key(curr);
                    temp1.add(curr_1);
                }
                Collections.sort(temp1);
                fin.add(temp1); 
            }

        }
    }

    public static ArrayList unique_Array(ArrayList res){
        ArrayList fin = new ArrayList<>();

        for (int i = 0; i<res.size();i++){
            if (!fin.contains(res.get(i))){
                ArrayList temp = new ArrayList();
                temp = (ArrayList)res.get(i);
                fin.add(temp);
            }
        }
        return (fin);
    }

    public static ArrayList anagram_word(ArrayList word){
        ArrayList anagram = new ArrayList<>();

        for (int i=0;i<word.size();i++){
            String curr = (String)word.get(i);
            ArrayList ana1 = create_anag_1(curr);
            anagram.add(ana1);
        }
        return (anagram);
    }

    public static ArrayList populate(ArrayList word){
        //Set<String> hash_Set = new HashSet<String>();
        int len = word.size();
        ArrayList<String> final_words_f = new ArrayList<>();

        if (len==2){
            ArrayList first = (ArrayList)word.get(0);
            ArrayList second = (ArrayList) word.get(1);

            for (int i=0;i<first.size();i++){
                for (int j=0;j<second.size();j++){
                    Set<String> hash_Set = new HashSet<String>();
                    String res = new String("");
                    String res1 = (String)first.get(i);
                    String res2 = (String)second.get(j);
                    res = res1 + " " + res2;
                    hash_Set.add(res);
                    //System.out.println(hash_Set);
                    res = res2 + " " + res1;
                    hash_Set.add(res);
                    ArrayList<String> final_words = new ArrayList<>(hash_Set);
                    final_words_f.addAll(final_words);
                }
            }
        }
        else if (len==3){
            ArrayList first = (ArrayList) word.get(0);
            ArrayList second = (ArrayList) word.get(1);
            ArrayList third = (ArrayList) word.get(2);
            ArrayList new_res = new ArrayList<>();
            for (int i=0;i<first.size();i++){
                for (int j=0;j<second.size();j++){
                    for (int k=0;k<third.size();k++){
                        Set<String> hash_Set = new HashSet<String>();
                        String res = new String("");
                        String res1 = (String)first.get(i);
                        String res2 = (String)second.get(j);
                        String res3 = (String)third.get(k);
                        res = res1 + " " + res2 + " " + res3;
                        hash_Set.add(res);
                        res = res1 + " " + res3 + " " + res2;
                        hash_Set.add(res);
                        res = res2 + " " + res1 + " " + res3;
                        hash_Set.add(res);
                        res = res2 + " " + res3 + " " + res1;
                        hash_Set.add(res);
                        res = res3 + " " + res1 + " " + res2;
                        hash_Set.add(res);
                        res = res3 + " " + res2 + " " + res1;
                        hash_Set.add(res);
                        ArrayList<String> final_words = new ArrayList<>(hash_Set);
                        final_words_f.addAll(final_words);
                    }
                }
            }
        }
        //ArrayList<String> final_words = new ArrayList<>(hash_Set);
        //System.out.println(final_words_f);
        return (final_words_f);
    }


    public static void create_anagram(String s){
        int len = s.length();
        ArrayList<String> one_word = new ArrayList<String>();
        ArrayList two_word = new ArrayList();
        ArrayList three_word = new ArrayList();

        ArrayList complete_list = new ArrayList();


        //one liner
        one_word = create_anag_1(s);
        
        if (one_word.size()>0){
            complete_list.addAll(one_word);
        }
        // for (int i = 0; i < one_word.size(); i++){
        //     complete_list.add((String)one_word.get(i));
        // }

        //two liner
        if (len>=6){
            ArrayList<ArrayList<String>> res = create_partition_main(s,2);
            ArrayList<ArrayList<String>> fin = new ArrayList<>();
            check_if_in_hash(res, fin);
            //System.out.println(hash.hash_table);
            //System.out.println(res);
            //System.out.println(fin);
            ArrayList all_valid = unique_Array(fin);
            //System.out.println(all_valid);
            ArrayList anagram = new ArrayList<>();
            ArrayList complete_anagram_2 = new ArrayList<>();
            for (int i =0; i<all_valid.size();i++){
                ArrayList temp = anagram_word((ArrayList)all_valid.get(i));
                //System.out.println(temp);
                anagram.add(temp);
            }
            //System.out.println(anagram);
            for (int j=0;j<anagram.size();j++){
                ArrayList temp =  (ArrayList)anagram.get(j);
                ArrayList words =  (ArrayList)populate(temp);
                complete_anagram_2.addAll(words);
            }
            //System.out.println(complete_anagram_2);
            Collections.sort(complete_anagram_2);
            //System.out.println(complete_anagram_2);
            if (complete_anagram_2.size()>0){
                complete_list.addAll(complete_anagram_2);
            }
            //System.out.println(complete_list);
            // for (int k=0;k<complete_anagram_2.size();k++){
            //     complete_list.add((String)complete_anagram_2.get(k));
            // }
        }

        //three linear
        if (len>=9){
            ArrayList<ArrayList<String>> res = create_partition_main(s,3);
            ArrayList<ArrayList<String>> fin = new ArrayList<>();
            check_if_in_hash(res, fin);
            //System.out.println(fin);
            ArrayList all_valid = unique_Array(fin);
            //System.out.println(all_valid);
            ArrayList anagram = new ArrayList<>();
            ArrayList complete_anagram_3 = new ArrayList<>();
            for (int i =0; i<all_valid.size();i++){
                ArrayList temp = anagram_word((ArrayList)all_valid.get(i));
                //System.out.println(temp);
                anagram.add(temp);
            }

            //System.out.println(anagram);
            for (int j=0;j<anagram.size();j++){
                ArrayList temp =  (ArrayList)anagram.get(j);
                ArrayList words =  (ArrayList)populate(temp);
                complete_anagram_3.addAll(words);
            }
            
            Collections.sort(complete_anagram_3);
            if (complete_anagram_3.size()>0){
                complete_list.addAll(complete_anagram_3);
            }
        }
            // for (int k=0;k<complete_anagram_3.size();k++){
            //     System.out.println((String)complete_anagram_3.get(k));
            // }

            //System.out.println(complete_list);
            //System.out.println("hi");
            Collections.sort(complete_list);
            //System.out.println(complete_list);
            //System.out.println("hi2");
            for (int k=0;k<complete_list.size();k++){
                System.out.println((String)complete_list.get(k));
            }
    }
        //System.out.print(-1);
    

    public static void main(String args[]){
        
        try {
            long startTime = System.nanoTime();
            File file = new File("C:/Users/navya/Desktop/Stuff/IIT/Sem-4/COL106/assi/a4/A4/2019CH10106/output.txt");
            PrintStream stream = new PrintStream(file);
            System.setOut(stream);
            File vocab = new File(args[0]);
            File input = new File(args[1]);
            BufferedReader br = new BufferedReader(new FileReader(vocab));
            int n = Integer.parseInt(br.readLine());
            String line;
            hash.capacity = capacity;
            while ((line = br.readLine()) != null) {
                hash.add(line);
                // line = br.readLine();
                // System.out.println(line);
            }
            //int index = hash.find("alan");
            //System.out.println(hash.hash_table.get(index).value);
           // hash.create_tables();
            
            BufferedReader br_new = new BufferedReader(new FileReader(input));
            int words = Integer.parseInt(br_new.readLine());
            //System.out.println(words);

            int j = 0;
            while ((line = br_new.readLine()) != null) {
                String s = line;
                int len = s.length();
                //divide into words of length 2
                j++;
                create_anagram(s);
                if (j==(words)){
                    System.out.print(-1);
                }
                else{
                    System.out.println(-1);
                }
            }
            System.out.println(hash.collision);
            //System.out.println("end");

            long endTime = System.nanoTime();
            long timeElapsed = endTime - startTime;
            System.out.println(timeElapsed);

        } catch (IOException e){
            System.out.println("File not found");
        }
    }


}
