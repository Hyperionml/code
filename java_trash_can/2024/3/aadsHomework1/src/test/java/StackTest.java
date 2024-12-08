import com.hyperionml.utils.impl.MyLinkedStack;
import org.junit.Test;

public class StackTest {

    private boolean isOperator(char ch){
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
    }

    double applyOp(double a, double b, char op) throws Throwable {
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0) {
                    throw new Throwable("被除数不能为0");
                }
                yield a / b;
            }
            default -> 0;
        };
    }

    private double evaluateExpression(String expression) throws Throwable {
        MyLinkedStack<Double> operandStack = new MyLinkedStack<>();
        MyLinkedStack<Character> operatorStack = new MyLinkedStack<>();
        char[] exp = expression.toCharArray();
        for (int i = 0; i < exp.length; ++i) {
            char ch = exp[i];
            if (Character.isDigit(ch)) {
                // 处理多位数
                double operand = 0;
                while (i < exp.length && Character.isDigit(exp[i])) {
                    operand = operand * 10 + (exp[i] - '0');
                    ++i;
                }
                --i; // 因为for循环还会递增i，所以需要减回来
                operandStack.push(operand);
            } else if (ch == ' ') {
                // 忽略空格
                continue;
            } else if (isOperator(ch)) {
                // 处理操作符
                while (!operatorStack.isEmpty() && precedence(ch) <= precedence(operatorStack.getTop())) {
                    double b = operandStack.pull();
                    double a = operandStack.pull();
                    char op = operatorStack.pull();
                    operandStack.push(applyOp(a, b, op));
                }
                operatorStack.push(ch);
            } else if (ch == '(') {
                // 处理左括号，压入操作符栈
                operatorStack.push(ch);
            } else if (ch == ')') {
                // 处理右括号
                while (!operatorStack.isEmpty() && operatorStack.getTop() != '(') {
                    double b = operandStack.pull();
                    double a = operandStack.pull();
                    char op = operatorStack.pull();
                    operandStack.push(applyOp(a, b, op));
                }
                if (!operatorStack.isEmpty()) operatorStack.pull(); // 弹出左括号
            } else {
                // 如果字符不是数字、空格、操作符或括号，则忽略
                throw new Throwable("非法字符:" + ch);
            }
        }

        try {
            // 应用剩余的操作符
            while (!operatorStack.isEmpty()) {
                double b = operandStack.pull();
                double a = operandStack.pull();
                char op = operatorStack.pull();
                operandStack.push(applyOp(a, b, op));
            }
        } catch (Throwable e) {
            throw new Throwable("表达式非法");
        }

        // 栈顶元素就是结果
        return operandStack.getTop();
    }

    @Test
    public void testShowAll(){
        MyLinkedStack<Integer> stack = new MyLinkedStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        stack.showAll();
    }

    @Test
    public void testNullStackPull() throws Throwable {
        MyLinkedStack<Integer> stack = new MyLinkedStack<>();
        stack.pull();
    }

    @Test
    public void testPull() throws Throwable {
        MyLinkedStack<Integer> stack = new MyLinkedStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        System.out.println(stack.pull());
        System.out.println("---------------");
        stack.showAll();
    }

    @Test
    public void testGetTop(){
        MyLinkedStack<Integer> stack = new MyLinkedStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        System.out.println(stack.getTop());
    }

    @Test
    public void testEvaluateExpression() throws Throwable {
        System.out.println(evaluateExpression("1 + (2 + 42) * 2 / 3"));
    }

}
