# 1. Issue with `getDFSWithVisitInfo` in the Test File


According to the PDF, the `getDFSWithVisitInfo` method is supposed to return a `List<Node>`, which represents the order in which the nodes are visited during the DFS traversal. However, in the test file (Part 6) that was provided, the return value is not being captured. This means that the output from the DFS traversal is not being printed, and the expected result is not shown when running the test.

## Problem

In the test file, the code looks like this:

```java
gLecture.getDFSWithVisitInfo(nodeVisit, edgeVisit);

System.out.println("Nodes visit info\n-----------------\n");
for (Node u: gLecture.getAllNodes()) {
    System.out.println(u + ": " + nodeVisit.get(u));
}
```


## 2. IOException in File Methods

### Description

Methods that read or write files can throw an `IOException` if the file doesn’t exist or can’t be accessed.
If these methods are used in a class that doesn’t handle exceptions, the program will crash or fail to compile.

### Fix

Either:

* Declare `throws IOException` in the method signature, or
* Wrap the call in a `try-catch` block.
