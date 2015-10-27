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
public class ReplSetTester{
    public static void main(String[] args) throws Exception{
        ReplSet<String> set1 = new ReplSet<>("pat");
        ReplSet<String> set2 = new ReplSet<>("pat");
        ReplSet<String> set3 = new ReplSet<>("pat");
        set1.add("Saya");
        set2.add("Ikut");
        set3.add("Kelas");
        set1.add("PAT");
        set3.remove("PAT");
        assert(!set1.contains("PAT") && !set2.contains("PAT") && !set3.contains("PAT") );
    }
}
