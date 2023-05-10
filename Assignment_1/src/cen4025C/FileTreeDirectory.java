package cen4025C;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileTreeDirectory 
{

	public static void main(String[] args) throws Exception
	{

		Path currentPath = Paths.get(System.getProperty("user.dir"));
		
		listDir(currentPath, 0);

	}

	public static void listDir(Path path, int depth) throws Exception
	{
		
		BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);

		
		// If directory, list the files and traverse down inside each of those
		if(attr.isDirectory())
		{
			DirectoryStream<Path> paths = Files.newDirectoryStream(path);
			
			System.out.println(spacesForDepth(depth) + ">" + path.getFileName());
			
			for(Path tempPath: paths)
			{
				listDir(tempPath, depth + 1);
			}
		}
		
		else 
		{
			System.out.println(spacesForDepth(depth) + "--" + path.getFileName() + " | File Size: " + attr.size() + " bytes");
		}	
	}
	
	public static String spacesForDepth(int depth)
	{
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < depth; i++)
		{
			builder.append("    ");
		}
		
		return builder.toString();
	}
	
	public static long getFileSize(File file)
	{
		long fileSize = 0;
		
		if(file.isDirectory())
		{
			File[] files = file.listFiles();
			for(int i = 0; files != null && i < files.length; i++)
			{
				fileSize += getFileSize(file);
			}
		}
		
		else
		{
			fileSize += file.length();
		}
		
		return fileSize;
	}
}