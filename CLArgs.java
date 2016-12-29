import java.util.ArrayList;
import java.io.*;

public class CLArgs
{
	private String[] args; 
	private ArrayList<Option> optionsAccepted; 
	private ArrayList<Option> optvals; 
	private int numArgs;

	public CLArgs(Option... accepted) {
		optvals = new ArrayList<Option>();
		optionsAccepted = new ArrayList<Option>();

		for(int i = 0; i < accepted.length; i++)
			optionsAccepted.add(accepted[i]);
	}

	public CLResult parse(String[] args) {
		this.args = args;

		for (int i = 0; i < args.length; i++) {
			String opt = args[i].toString();
			boolean optflag = false;

			if (opt.charAt(0) == '-') {
				optflag = true;

				if (opt.charAt(1) == '-') 
					opt = opt.substring(2, opt.length());

				else 
					opt = opt.charAt(1) + "";
			}


			if (optflag) {

				if (optionsAccepted.contains(new Option(opt, null))) //if this is an accepted option, add the next arg as the options value. Otherwise, this is an argument
					optvals.add(new Option(opt, args[++i]));

				else 
					return CLResult.INVALIDARG;

			} else {

				optvals.add(new Option(null, args[i]));
				numArgs++;
			}
		}

		for (Option accepted : optionsAccepted) {
			if (accepted.isRequired() && !optvals.contains(accepted)) {
				return CLResult.MISSINGARG;
			}
		}

		return CLResult.SUCCESS;
	}

	public String getOpt(String name) {
		String val = null;
		for (int i = 0; i < optvals.size(); i++) {
			if (optvals.get(i).getName() != null && optvals.get(i).getName().equals(name))
				val = optvals.get(i).getValue();
		}
		return val;
	}

	public String getArg(int n) {
		int i = 1;
		for (Option opt : optvals) {
			if (opt.getName() == null) {
				if (i == n)
					return opt.getValue();
				else 
					i++;
			}
		}
		return null;
	}

	public int argc() {
		return numArgs;
	}
}

class Option {
	private String name, value;
	private boolean required;

	public Option(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public Option(String name, boolean required) {
		this.name = name;
		this.required = required;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public boolean isRequired() {
		return required;
	}

	public boolean equals(Object o) {

		System.out.println("entered");

		if (this == o) return true;
		if (!(o instanceof Option)) return false;

		Option that = (Option)o;
		System.out.println(this.name + " " +that.name);
		return this.name.equals(that.name);
	}
}

enum CLResult {
	SUCCESS, MISSINGARG, INVALIDARG
}
