import com.hyperionml.pojo.TreeNode;
import org.junit.Test;


public class BinarySearchTest {

    @Test
    public void bsTest() {
        int[] arr = new int[]{2, 4, 6, 7, 9, 10, 13, 15, 20};//顺序数组

        System.out.println(binarySearch(13, arr));//二分查找13

        System.out.println(binarySearch(14, arr));//二分查找14
    }

    //二分查找
    public int binarySearch(int key, int[] arr){
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;//这里是2进制运算里的右移运算，右移1在算数中代表除以2，
            long midVal = arr[mid];      //使用这种方法可以防止前后两个指针相加结果溢出而导致计算出错
            if (midVal < key)
                low = mid + 1;
            else if (midVal > key)
                high = mid - 1;
            else
                return mid; //返回的是索引
        }
        return -1;  //没查到就返回-1
    }

    @Test
    public void binarySearchTreeTest() throws Throwable {
        //这一步是在创建二叉查找树，使用的是上面的顺序数组构建的，根节点是9的平衡二叉查找树
        TreeNode binarySearchTree = new TreeNode(
                new TreeNode(new TreeNode(new TreeNode((char) 2), (char) 4, null), (char) 6, new TreeNode((char) 7)),
                (char) 9,
                new TreeNode(new TreeNode(new TreeNode((char) 10), (char) 13, null), (char) 15, new TreeNode((char) 20)));
        //这里调用的就是写在类里的中序遍历，遍历结果就是将树还原成顺序数组。
        binarySearchTree.iterator2(character -> System.out.println((int)character));
    }
}
