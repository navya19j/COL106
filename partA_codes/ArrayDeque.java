import java.lang.reflect.Array;
import java.util.Arrays;

public class ArrayDeque implements DequeInterface {
	Object[] deck = new Object[1];
	int f = 0;
	int l = 0;
	int size_del=1;
	public void insertFirst(Object o) {
		int n = deck.length;
		try {
			if (n!=1){
				if ((f == 0 && l == (n - 1)) || (l == f - 1)) {
					Object[] deck_n = new Object[2 * n];
					int i = f;
					int k = 0;
					while (i != l){
						Object temp = deck[i];
						deck_n[k] = temp;
						i = (i + 1) % n;
						k++;
					}
					deck_n[k] = deck[l];
					deck = deck_n;
					l = n - 1;
					f = 2 * n - 1;
					deck[f] = o;
				} else {
					f = (f - 1 + (n)) % (n);
					deck[f] = o;
				}
			}
			else{
				if (f==0 && l==0){
					if (deck[f]!=null){
						deck[f]=o;
					}
					else{
						Object[] deck_n = new Object[2 * n];
					int i = f;
					int k = 0;
					while (i != l) {
						Object temp = deck[i];
						deck_n[k] = temp;
						i = (i + 1) % n;
						k++;
					}
					deck_n[k] = deck[l];
					deck = deck_n;
					l = n - 1;
					f = 2 * n - 1;
					deck[f] = o;
					}
				}
				else{
					deck[f] = o;
				}
			}

		}
		// System.out.println(Arrays.toString(deck));
		catch (Exception e) {
			throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		}
	}

	public void insertLast(Object o) {
		try {
			int n = deck.length;
			if (n!=1){
				if ((f == 0 && l == (n - 1)) || (l == f - 1)) {
					Object[] deck_n = new Object[2 * n];
					int i = f;
					int k = 0;
					while (i != l) {
						Object temp = deck[i];
						deck_n[k] = temp;
						i = (i + 1) % n;
						k++;
					}
					deck_n[k] = deck[l];
					deck = deck_n;
					f = 0;
					l = n;
					deck[l] = o;
				}
			
				else {
					l = (l + 1) % (n);
					deck[l] = o;
				}
			}
			else{
				if (f==0 && l==0){
					if (deck[l]!=null){
						deck[l] = o;
					}
					else{
						Object[] deck_n = new Object[2 * n];
						int i = f;
						int k = 0;
						while (i != l) {
							Object temp = deck[i];
							deck_n[k] = temp;
							i = (i + 1) % n;
							k++;
						}
						deck_n[k] = deck[l];
						deck = deck_n;
						f = 0;
						l = n;
						deck[l] = o;
					}
				}
				else{
					deck[l]=0;
				}
			}
		}
		// System.out.println(Arrays.toString(deck));
		catch (Exception e) {
			throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		}
	}

	public Object removeFirst() throws EmptyDequeException {
		int n = deck.length;
		// you need to implement this
		Object o;
		try {
				if (deck[f]!=null){
					o = deck[f];
					f = (f + 1) % (n);
					return (o);
				}
				else{
					while(deck[f]==null){
						f = (f + 1) % (n);
					}
					o = deck[f];
					return(o);
				}
			}
		catch (Exception e) {
			throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		}
	}

	public Object removeLast() throws EmptyDequeException {
		int n = deck.length;
		// you need to implement this
		Object o;
		try {
				if (deck[l]!=null){
					o = deck[l];
					l = (l - 1 + n) % (n);
					return (o);
				}
				else{
					while (deck[l]==null){
						l = (l - 1 + n) % (n);
					}
					o = deck[l];
					return o;
				}
			}
		catch (Exception e) {
			throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		}
	}

	public Object first() throws EmptyDequeException {
		Object o;
		try {
			// you need to implement this
			o = deck[f];
			return (o);
		}
		catch (Exception e) {
			throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		}
	}

	public Object last() throws EmptyDequeException {
		Object o;
		try {
			// you need to implement this
				o = deck[l];
				return (o);
		} catch (Exception e) {
			throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		}
	}

	public int size() {
		// you need to implement this
		int n = deck.length;
		try {
			int s;
			if ((f==0) && (l==(n-1))) {
				s = n;
			} else if (size_del==0) {
				s = 0;
			} else {
				s = (n - f + l) % (n);
			}
			return (s);
		} catch (Exception e) {
			throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		}
	}

	public boolean isEmpty() {
		// you need to implement this
		int n = deck.length;
		try {
			if (f==l) {
				return (true);
			}
			return false;
		} catch (Exception e) {
			throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		}
	}

	public String toString() {
		// you need to implement this
		int n = deck.length;
		// System.out.println(f);
		// System.out.println(l);
		try {
			StringBuffer fin = new StringBuffer("[");
			int i = f;
			while (i != l) {
				if (deck[i]!=null){
					fin.append(deck[i]);
					fin.append(",");
				}
				i = (i + 1) % n;
			}
			if (deck[l]!=null){
				fin.append(deck[l]);
				fin.append("]");
			}
			String s = String.valueOf(fin);
			// System.out.println(Arrays.toString(deck));
			return (s);
		} catch (Exception e) {
			throw new java.lang.UnsupportedOperationException("Not implemented yet.");
		}
	}
}