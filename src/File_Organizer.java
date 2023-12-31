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
	public void organizeModFiles ()
	{
		organizeSharedModFiles(BaseModPath, MergeModPath, "");
	}

	public void organizeSharedModFiles (File baseDir, File mergeDir, String trackedPath)
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
					// System.out.print("true ");
					if (baseSubFiles[bIdx].isDirectory()) // If it is a directory
					{
						// System.out.println("directory");
						trackedPath += "\\" + baseSubFiles[bIdx].getName(); // Add information to currentFilePath
						organizeSharedModFiles(baseSubFiles[bIdx], mergeSubFiles[mIdx], trackedPath); // Recurse function on appropriate sub directories
					}
					else // If it is a matching file between base and merge mods
					{
						// System.out.println("file");
						try
						{
							FileUtils.moveToDirectory(baseSubFiles[bIdx] // Move matching base file to proper output directory
													, new File(BaseSharedOutPath.getAbsolutePath() + trackedPath)
													, true);
							FileUtils.moveToDirectory(mergeSubFiles[mIdx] // Move matching merge file to proper output directory
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
		}
		/*\ 	// If no merge file/directories match the checked base file
		// 	targetDir = new File(UniqueOutPath.getName() + "\\" + trackedPath); // Debug
		// 	System.out.println(targetDir.getAbsolutePath());
		// 	try
		// 	{
		// 		System.out.println(baseSubFiles[bIdx].getName()); // Debug
		// 		// Move to UniqueOutPath
		// 		FileUtils.moveToDirectory(baseSubFiles[bIdx], targetDir, true); 
		// 	}
		// 	catch (IOException ioe)
		// 	{
		// 		System.err.println("Error moving " + baseSubFiles[bIdx].getName() + " to proper directory");
		// 		ioe.printStackTrace();
		// 	}
		} 
		// // At this point there should be no more base files in the current directory
		// mergeSubFiles = mergeDir.listFiles(); // Update to remaining merge files 
		// for (File f : mergeSubFiles) // Move remaining files to UniqueOutPath in appropriate sub directory
		// {
		// 	try
		// 	{
		// 		FileUtils.moveToDirectory(f, targetDir, true); 
		// 	}
		// 	catch (IOException ioe)
		// 	{
		// 		System.err.println("Error moving " + f.getName() + " to proper directory");
		// 		ioe.printStackTrace();
		// 	}	
		\*/
		moveRemainingFiles(); 
	}

	private void moveRemainingFiles ()
 	{
		for (File bf : BaseModPath.listFiles()) 
		{
			
			try
			{
				FileUtils.copyToDirectory(bf, UniqueOutPath);
				bf.delete();
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
		for (File mf : MergeModPath.listFiles()) 
		{
			try
			{
				FileUtils.copyToDirectory(mf, UniqueOutPath);
				mf.delete();
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
	}

	public void clearDirectories () throws IOException
	{
		for (File f : BaseSharedOutPath.listFiles()) 
		{
			FileUtils.forceDelete(f);
		}
		for (File f : MergeSharedOutPath.listFiles()) 
		{
			FileUtils.forceDelete(f);
		}
		for (File f : UniqueOutPath.listFiles()) 
		{
			FileUtils.forceDelete(f);
		}
	}
}