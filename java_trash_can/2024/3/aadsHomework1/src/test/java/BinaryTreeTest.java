
import com.hyperionml.pojo.TreeNode;
import com.hyperionml.utils.impl.MyLinkedStack;
import org.junit.Test;


public class BinaryTreeTest {

    public static void main(String[] args) throws Throwable {
        TreeNode root = new TreeNode(
                new TreeNode(new TreeNode('D'), 'B', null),
                'A',
                new TreeNode(new TreeNode('E'), 'C', new TreeNode('F'))
        );

        MyLinkedStack<TreeNode> stack = new MyLinkedStack<>();

        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()){
            if(curr != null){
                System.out.println("去:" + curr.val);//前序遍历位置
                stack.push(curr);
                curr = curr.left;
            }
            else {
                TreeNode peek = stack.getTop(); // 栈顶元素
                if(peek.right == null){ //右子树处理完成
                    TreeNode pull = stack.pull();
                    System.out.println("回:" + pull.val);
                } else {
                    curr = peek.right;
                }
            }
        }
    }

    @Test
    public void testIterator() throws Throwable {
        TreeNode root = new TreeNode(
                new TreeNode(new TreeNode('D'), 'B', null),
                'A',
                new TreeNode(new TreeNode('E'), 'C', new TreeNode('F'))
        );

        root.iterator3(System.out::println);
    }

    @Test
    public void testGetDepth() {
        TreeNode root = new TreeNode(
                new TreeNode(new TreeNode('D'), 'B', null),
                'A',
                new TreeNode(new TreeNode('E'), 'C', new TreeNode('F'))
        );

        System.out.println(root.getDepth());
    }

    @Test
    public void testGetLeafCount(){
        TreeNode root = new TreeNode(
                new TreeNode(new TreeNode('D'), 'B', null),
                'A',
                new TreeNode(new TreeNode('E'), 'C', new TreeNode('F'))
        );

        System.out.println(root.getLeafCount());
    }

    @Test
    public void testGetNodeLevel() {
        TreeNode root = new TreeNode(
                new TreeNode(new TreeNode('D'), 'B', null),
                'A',
                new TreeNode(new TreeNode('E'), 'C', new TreeNode('F'))
        );

        System.out.println(root.getNodeLevel('B'));
    }
}
