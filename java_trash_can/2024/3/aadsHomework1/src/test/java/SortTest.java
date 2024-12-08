import com.hyperionml.utils.impl.SortUtils;
import org.junit.Test;


public class SortTest {

    @Test
    public void selectionSortTest() {
        int[] arr = new int[]{3,5,1,6,7,2,8,4};
        SortUtils.selectionSort(arr);
    }

    @Test
    public void bubbleSortTest() {
        int[] arr = new int[]{3,5,1,6,7,2,8,4};
        SortUtils.bubbleSort(arr);
    }

    @Test
    public void shellSortTest() {
        int[] arr = new int[]{3,5,1,6,7,2,8,4};
        SortUtils.shellSort(arr);
    }

    @Test
    public void quickSortTest() {
        int[] arr = new int[]{3,5,1,6,7,2,8,4};
        SortUtils.quickSort(0, 7, arr);
    }

    @Test
    public void testInsertionSort() {
        int[] arr = new int[]{3,5,1,6,7,2,8,4};
        SortUtils.insertionSort(arr);
    }

    @Test
    public void testHeapSort() {
        int[] arr = new int[]{3,5,1,6,7,2,8,4};
        SortUtils.heapSort(arr);
    }
}
