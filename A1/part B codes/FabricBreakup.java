import java.io.*;
import java.util.EmptyStackException;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

public class FabricBreakup {
	public static void main(String args[]) {
		// Implement FabricBreakup puzzle using Stack interface
		File file = new File(args[0]);
		Scanner sc;
		try {
			sc = new Scanner(file);
			int n = sc.nextInt();
			StackInterface good = new Stack();
			StackInterface count = new Stack();
			int m;
			int like;
			int max;
			
			for (int i = 0; i < n; i++) {
				int op = sc.nextInt();
				int b_shirt = sc.nextInt();
				if (b_shirt == 1) {
					like = sc.nextInt();
					try{
						if (good.isEmpty()==false){
							max = (int)good.top();
						}else{
							max = -1;
						}
					}
					catch(Exception e){
						max = -1;
					}
					if (like>=max){
						max = like;
						good.push(max);
						count.push(0);
					}
					else{
						try{
							int l = (int)count.pop();
							count.push(l+1);
						}catch(Exception e){
							System.out.println(op+" "+"-1");
						}
					}
				}else if (b_shirt == 2){
					try{
						if (good.isEmpty()==false){
						int k = (int)count.pop();
						int x = (int)good.pop();
						
						System.out.println(op+" "+k);
						if (good.isEmpty()==false){
							max = (int)good.top();
						}
						else{
							max = -1;	
						}					
					}
					else{
						System.out.println(op+" "+"-1");
					}
					}catch (Exception e){
						System.out.println(op+" "+"-1");
					}
					
				}
			}
		}catch (IOException e) {
			System.out.println("File not found");
		}
	}
}
