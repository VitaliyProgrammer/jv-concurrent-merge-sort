package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {

    private static final int THRESHOLD = 10;
    private final int[] array;
    private final int left;
    private final int right;


    public MergeSortAction(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    @Override
    protected void compute() {
        if (right - left <= THRESHOLD) {
            System.out.println("left=" + left + " right=" + right);
            Arrays.sort(array, left, right);
            return;
        }

        int middle = (left + right) / 2;

        MergeSortAction leftTask = new MergeSortAction(array, left, middle);
        MergeSortAction rightTask = new MergeSortAction(array, middle, right);

        leftTask.fork();
        rightTask.compute();
        leftTask.join();

        merge(array, left, middle, right);
    }

    private void merge(int[] array, int left, int middle, int right) {

        int[] temp = new int[right - left];

        int i = left;
        int j = middle;
        int k = 0;

        while (i < middle && j < right) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i < middle) {
            temp[k++] = array[i++];
        }

        while (j < right) {
            temp[k++] = array[j++];
        }

        System.arraycopy(temp, 0, array, left, temp.length);
    }
}
