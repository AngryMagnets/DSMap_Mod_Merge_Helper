public class App 
{
    public static void main(String[] args) throws Exception 
    {
        File_Organizer fo = new File_Organizer
        ( "D:\\Documents\\Scripts\\Java Projects\\DSMap_Mod_Merge_Helper\\BaseModPath"
        , "D:\\Documents\\Scripts\\Java Projects\\DSMap_Mod_Merge_Helper\\MergeModPath"
        , "D:\\Documents\\Scripts\\Java Projects\\DSMap_Mod_Merge_Helper\\BaseModSharedOutput"
        , "D:\\Documents\\Scripts\\Java Projects\\DSMap_Mod_Merge_Helper\\MergeModSharedOutput"
        , "D:\\Documents\\Scripts\\Java Projects\\DSMap_Mod_Merge_Helper\\MergeModUniqueOutput");

        // fo.clearDirectories();
        fo.saveSameFiles();

        // Epic
    }
}
