/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package patjgroups;

/**
 *
 * @author M. Reza Irvanda
 */
public class ReplStackTester{
    public static void main(String[] args) throws Exception{
        ReplStack<String> stack1 = new ReplStack<>("pat_stack");
        ReplStack<String> stack2 = new ReplStack<>("pat_stack");
        ReplStack<String> stack3 = new ReplStack<>("pat_stack");
        stack1.push("Saya");
        stack2.push("Ikut");
        stack3.push("Kelas");
        stack1.push("PAT");
        stack2.pop();
        stack3.push("Joshua");
        assert(!stack1.top().equals("Joshua") && stack2.top().equals("Joshua") && stack3.top().equals("Joshua") );
    }
}
