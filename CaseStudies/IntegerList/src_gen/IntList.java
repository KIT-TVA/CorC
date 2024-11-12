public class IntList {

    public int[] data;
    public int LIMIT;

    /*@ invariant data != null; @*/
    /*@ invariant this != null; @*/


	/*@
	@ normal_behavior
	@ requires true;
	@ ensures containsNewTop(data, newTop)&& containsOldElements(data, \old(data));
	@ assignable data[*];
	@*/
	public void original_original_push(int newTop) {
		int i;
		int[] tmp;
		tmp = new int[data.length+1];
		tmp[tmp.length-1] = newTop;
		i = 0;
		while (i < data.length) {
			tmp[i] = data[i];
			i++;
		}
		data = tmp;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (\old(data).length < LIMIT) ==> \original_post;
	@ assignable \nothing;
	@*/
	public void original_push(int newTop) {
		if (data.length < LIMIT) {
			original_original_push(newTop);
		} else if (data.length >= LIMIT) {
			;
		}

	}

	/*@
	@ normal_behavior
	@ requires \original_pre && sorted(data);
	@ ensures \original_post && sorted(data);
	@ assignable data[*];
	@*/
	public void push(int newTop) {
		original_push(newTop);
		sort();

	}

	/*@
	@ normal_behavior
	@ requires data == \old(data);
	@ ensures containsOldElements(data, \old(data))&& sorted(data);
	@ assignable data[*];
	@*/
	public void sort() {
		int tmp;
		int j;
		int i;
		i = 0;
		while (i < data.length) {
			j = data.length-2;
			while (j>=i) {
				if (data[j] > data[j+1]) {
					tmp = data[j];
					data[j] = data[j+1];
					data[j+1] = tmp;
				} else if (data[j] <= data[j+1]) {
					;
				}
				j--;
			}
			i++;
		}

	}
}