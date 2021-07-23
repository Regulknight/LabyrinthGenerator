import java.util.LinkedList;
import java.util.List;

public class PathTree {
    private Cell root;

    private List<Branch> branches;

    public PathTree(Cell root, List<Branch> branches) {
        this.root = root;
        this.branches = branches;
    }

//    public PathTree updateTree(WorldMap map) {
//        List<Branch> newBranches = new LinkedList<>();
//
//        for (Branch branch: this.branches) {
//            newBranches.add(branch.updateBranch(map));
//        }
//
//       return new PathTree(this.root, newBranches);
//    }

}
