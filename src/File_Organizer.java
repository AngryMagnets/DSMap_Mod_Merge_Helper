import java.util.*;
import java.io.*;
import org.apache.commons.io.FileUtils;


public class File_Organizer
{
	private File BaseModPath, MergeModPath, BaseSharedOutPath, MergeSharedOutPath, UniqueOutPath;
	
	public File_Finder (String bp, String mp, String bop, String mop, String nop)
	{
		BaseModPath = new File(bp);
		MergeModPath = new File(mp);
		BaseSharedOutPath = new File(bop);
		MergeSharedOutPath = new File(mop);
		UniqueOutPath = new File(nop);
	}
	
	public boolean saveSameFiles ()
	{	
		File[] pbsub, pmsub, pbsubfiles, pmsubfiles;
		try 
		{
			for (File f : MergeModPath.listFiles())
			{
				FileUtils.copyToDirectory(f, UniqueOutPath);
			}
			pbsub = BaseModPath.listFiles(); pmsub = UniqueOutPath.listFiles();
			for (int i = 0; i < pbsub.length; i++)
			{
				pbsubfiles = pbsub[i].listFiles();
				
				for (int j = 0; j < pmsub.length; j++)
				{
					pmsubfiles = pmsub[j].listFiles();
					if (!pbsub[i].getName().equals(pmsub[j].getName()))
					{
						continue;
					}
					for (int k = 0; k < pbsubfiles.length; k++)
					{
						for (int l = 0; l < pmsubfiles.length; l++)
						{
							if (pbsubfiles[k].getName().equals(pmsubfiles[l].getName()))
							{
								FileUtils.copyToDirectory(pbsubfiles[k], new File(BaseSharedOutPath.getName() + "\\" + pbsub[i].getName()));
								FileUtils.moveFileToDirectory(pmsubfiles[l], new File(MergeSharedOutPath.getName() + "\\" + pmsub[j].getName()), true);
							}
							if (l == pmsubfiles.length - 1 && pmsub[j].list() == null)
							{
								FileUtils.deleteDirectory(pmsub[j]);
							}
						}
					}
				}
			}
		}
		catch (IndexOutOfBoundsException ioobe)
		{
			System.out.println("Stoopid idjhut cam't pwogwam");
		}
		catch (Exception e)
		{
			System.err.println("Check input and output file paths");
		}
		return false;
	}
}