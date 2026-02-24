* **Language:** Java
* **Build Tool:** Maven
* **Testing:** JUnit 5

The core logic is contained entirely within the `FileCabinet` class. 
A private helper method (`flattenFolders`) was introduced to recursively extract all folders and subfolders into 
a flat list, ensuring adherence to the DRY  principle across all interface methods.

The project includes a comprehensive suite of unit tests verifying both standard and edge cases.
