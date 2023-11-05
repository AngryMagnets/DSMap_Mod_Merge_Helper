//import java.util.*;
import java.io.*;
import org.apache.commons.io.FileUtils;


public class File_Organizer
{
	private File BaseModPath, MergeModPath, BaseSharedOutPath, MergeSharedOutPath, UniqueOutPath;
	
	public File_Organizer (String bp, String mp, String bop, String mop, String nop)
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
								FileUtils.copyToDirectory(pbsubfiles[k], new File(BaseSharedOutPath.getName() 
								+ "\\" + pbsub[i].getName()));
								FileUtils.moveFileToDirectory(pmsubfiles[l], new File(MergeSharedOutPath.getName() 
								+ "\\" + pmsub[j].getName()), true);
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

	public boolean organizeModFiles (File baseDir, File mergeDir, String trackedPath)
	{
		// Get all sub files and dirs for base and merge mod in the target directory
		File[] baseSubFiles = baseDir.listFiles()
			 , mergeSubFiles = mergeDir.listFiles();

		for (int bIdx = 0; bIdx < baseSubFiles.length; bIdx++) // Get the file at the nth idx of the base files
		{	
			for (int mIdx = 0; mIdx < mergeSubFiles.length; mIdx++) // Get the file at the mth idx of the merge files
			{
				if (baseSubFiles[bIdx].getName().equals(mergeSubFiles[mIdx].getName())) // if file names match
				{
					if (baseSubFiles[bIdx].isDirectory()) // If it is a directory
					{
						trackedPath += "\\" + baseSubFiles[bIdx].getName(); // Add information to currentFilePath
						return organizeModFiles(baseSubFiles[bIdx], mergeSubFiles[mIdx], trackedPath); // Recurse function on appropriate sub directories
					}
					else // If it is a matching file between base and merge mods
					{
						try
						{
							FileUtils.moveToDirectory( baseSubFiles[bIdx] // Move matching base file to proper output directory
													 , new File(BaseSharedOutPath.getAbsolutePath() + trackedPath)
													 , true);
							FileUtils.moveToDirectory( mergeSubFiles[mIdx] // Move matching merge file to proper output directory
													 , new File(MergeSharedOutPath.getAbsolutePath() + trackedPath)
													 , true);	
						}
						catch (IOException ioe)
						{
							System.err.println("Error moving " + baseSubFiles[bIdx].getName() + " to proper directory");
							ioe.printStackTrace();
						}	
					}
				}
			}	
			// If no merge file/directories match the checked base file
			try
			{
				// Move to UniqueOutPath
				FileUtils.moveToDirectory(baseSubFiles[bIdx], new File(UniqueOutPath.getName() + "\\" + trackedPath), true); 
			}
			catch (IOException ioe)
			{
				System.err.println("Error moving " + baseSubFiles[bIdx].getName() + " to proper directory");
				ioe.printStackTrace();
			}
		} // At this point there should be no more base files in the current directory
		
		mergeSubFiles = mergeDir.listFiles(); // update to remaining merge files 
		for (File f : mergeSubFiles) 
		{
			try
			{
				FileUtils.moveToDirectory(f, new File(UniqueOutPath.getName() + "\\" + trackedPath), true); 
			}
			catch (IOException ioe)
			{
				System.err.println("Error moving " + baseSubFiles[bIdx].getName() + " to proper directory");
				ioe.printStackTrace();
			}	
		}
		return true;
	}
		/*\
		 * if (match & file)
		 * {
		 * 	baseFile.sendToAppropriateSubDirInBaseSharedOutPath DO NOT COPY, only move
		 * 	mergeFile.sendToAppropriateSubDirInMergeSharedOutPath DO NOT COPY, only move
		 * }
		 * if (noMatchesForNthBaseFile/Dir)
		 * {
		 * 	baseFile.sendToAppropriateSubDirInUniqueOutPath DO NOT COPY, only move
		 * }
		 * 
		 * 
		 * Once (baseSubFiles is empty AND mergeFiles remain)
		 * {
		 * 	mergeFiles.sendToAppropriateSubDirInUniqueOutPath DO NOT COPY, only move
		 * 	return true;
		 * }
		\*/	
	public void clearDirectories () throws IOException
	{
		for (File f : BaseSharedOutPath.listFiles()) 
		{
			FileUtils.delete(f);
		}
		for (File f : MergeSharedOutPath.listFiles()) 
		{
			FileUtils.delete(f);
		}
		for (File f : UniqueOutPath.listFiles()) 
		{
			FileUtils.delete(f);
		}
	}
}