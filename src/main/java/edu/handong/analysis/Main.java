package edu.handong.analysis;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Main {
	String inputPath;
	String outputPath;
	String anlysis;
	String coursecode;
	String startyear;
	String endyear;
	boolean help;
	boolean isAnlysisTwo;
	
	public static void main(String[] args) {
		Main myMain = new Main();
		myMain.run(args);
	
//		HGUCoursePatternAnalyzer analyzer = new HGUCoursePatternAnalyzer();
//		analyzer.run(args);
	}

	private void run(String[] args) {
		Options options = createOptions();
		
		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
			}
			
			String[] str = {inputPath, outputPath, startyear, endyear, anlysis, coursecode};
			
			HGUCoursePatternAnalyzer analyzer = new HGUCoursePatternAnalyzer();
			analyzer.run(str);
				
		}
		
		
	}

	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			inputPath = cmd.getOptionValue("i");
			outputPath = cmd.getOptionValue("o");
			anlysis = cmd.getOptionValue("a");
			if(anlysis.equals("1")) {
				isAnlysisTwo = false;
			} else if(anlysis.equals("2")) {
				isAnlysisTwo = true;
			}
			if(isAnlysisTwo)
				coursecode = cmd.getOptionValue("c");
			
			startyear = cmd.getOptionValue("s");
			endyear = cmd.getOptionValue("e");
			help = cmd.hasOption("h");

		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}

	private Options createOptions() {
		Options options = new Options();

		// add options by using OptionBuilder
		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.hasArg()
				.argName("Input path")
				.required()
				.build());

		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an output file path")
				.hasArg()     
				.argName("Output path")
				.required()
				.build());
		
		options.addOption(Option.builder("a").longOpt("analysis")
		        .desc("1: Count courses per semester, 2: Count per course name and year")
		        .hasArg()
		        .argName("Analysis option")
		        .required()
		        .build());
		
		options.addOption(Option.builder("c").longOpt("coursecode")
				.desc("Course code for '-a 2' option")
				.hasArg()
				.argName("course code") // only for '-a 2'
				.build());
		
		options.addOption(Option.builder("s").longOpt("startyear")
				.desc("Set the start year for analysis e.g., -s 2002")
				.hasArg()
				.argName("Start year for analysis")
				.required()
				.build());
		
		options.addOption(Option.builder("e").longOpt("endyear")
				.desc("Set the end year for analysis e.g., -e 2005")
				.hasArg()
				.argName("End year for analysis")
				.required()
				.build());
		
		options.addOption(Option.builder("h").longOpt("help")
		        .desc("Show a Help page")
		        .argName("Help")
		        .build());

		return options;	
	}
	
	private void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Course Analyzer";
		String footer ="";
		formatter.printHelp("The first argument of the printHelp method is \"HGUCourseCounter\".", header, options, footer, true);
		
	}
}