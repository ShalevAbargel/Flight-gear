package interpreter;

public class MyParser implements Parser{

	@Override
	public int parse(Lexer l, String[] lines) {
		boolean flag;
		String[] line=null;
		String[] copy=null;
		for(int i=0; i < lines.length;i++)
		{
			flag = false;
			line = l.lexer(lines[i]);
			if(line[0].equals("return"))
				return MyInterpreter.commandsTable.get(line[0]).doCommand(line);
			if(line[0].equals("while"))
			{
				StringBuilder sb = new StringBuilder();
				sb.append(lines[i]+ ";");
				for(int j = i+1; j < lines.length; j++)
				{
					if(!lines[j].contains("}"))
						sb.append(lines[j]+";");
					else
					{
						sb.append(lines[j]);
						i=j;
						MyInterpreter.commandsTable.get("while").doCommand(sb.toString().split(";"));
					}
				}
				continue;
			}
			for(String s: MyInterpreter.commandsTable.keySet())
			{
				if(line[0].equals(s))
				{
					copy = new String[line.length-1];
					for(int j=1; j<line.length;j++)
						copy[j-1] = line[j];
					if(MyInterpreter.commandsTable.get(line[0]).doCommand(copy) == 0)
					{
						flag = true;
						continue;
					}
				}
			}
			if(!flag)
			{
				StringBuilder sb = new StringBuilder();
				for(int k =0; k < line.length; k++)
					sb.append(line[k]);
				if(sb.toString().contains("=bind"))
				{
					String[] equalsBind = sb.toString().split("=bind");
					MyInterpreter.commandsTable.get("bind").doCommand(equalsBind);
				}
				else {
					String[] equals = sb.toString().split("=");
					MyInterpreter.commandsTable.get("=").doCommand(equals);
				}
			}
		}
		return 0;
		
	}

}
