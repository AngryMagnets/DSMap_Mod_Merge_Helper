import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.*;

public class App 
{
    static File_Organizer fo;
    public static void main(String[] args) throws Exception 
    {
        testInit();
        fo.organizeModFiles();

        // Epic
    }
    @Before
    public static void testInit () throws IOException
    {
        fo = new File_Organizer
        ( "D:\\Documents\\Scripts\\Java Projects\\DSMap_Mod_Merge_Helper\\BaseModPath"
        , "D:\\Documents\\Scripts\\Java Projects\\DSMap_Mod_Merge_Helper\\MergeModPath"
        , "D:\\Documents\\Scripts\\Java Projects\\DSMap_Mod_Merge_Helper\\BaseModSharedOutput"
        , "D:\\Documents\\Scripts\\Java Projects\\DSMap_Mod_Merge_Helper\\MergeModSharedOutput"
        , "D:\\Documents\\Scripts\\Java Projects\\DSMap_Mod_Merge_Helper\\UniqueOutput");

        File bm = new File("D:\\Documents\\Scripts\\Java Projects\\DSMap_Mod_Merge_Helper\\BaseModPath")
           , mm = new File("D:\\Documents\\Scripts\\Java Projects\\DSMap_Mod_Merge_Helper\\MergeModPath")
           , bo = new File("D:\\Documents\\Scripts\\Java Projects\\DSMap_Mod_Merge_Helper\\BaseModSharedOutput")
           , mo = new File("D:\\Documents\\Scripts\\Java Projects\\DSMap_Mod_Merge_Helper\\MergeModSharedOutput")
           , uo = new File("D:\\Documents\\Scripts\\Java Projects\\DSMap_Mod_Merge_Helper\\UniqueOutput")
           , bt = new File("D:\\Documents\\Scripts\\Java Projects\\DSMap_Mod_Merge_Helper\\BaseModTestFiles")
           , mt = new File("D:\\Documents\\Scripts\\Java Projects\\DSMap_Mod_Merge_Helper\\MergeModTestFiles");
        
           for (File bf : bm.listFiles()) 
           {
                FileUtils.forceDelete(bf);
           }
           for (File mf : mm.listFiles()) 
           {
                FileUtils.forceDelete(mf);
           }

           for (File of : bo.listFiles()) 
           {
                FileUtils.forceDelete(of);
           }
           for (File of : mo.listFiles()) 
           {
                FileUtils.forceDelete(of);
           }
           for (File of : uo.listFiles()) 
           {
                FileUtils.forceDelete(of);
           }

           for (File bf : bt.listFiles()) 
           {
                FileUtils.copyDirectoryToDirectory(bf, bm);
           }
           for (File mf : mt.listFiles()) 
           {
                FileUtils.copyDirectoryToDirectory(mf, mm);
           }
    }

    @Test 
    public static void testFileOrganization () throws Exception
    {
        fo.organizeModFiles();
    }
}
