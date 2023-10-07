// A program to demonstrate Bankers Algorithm.
// It will take N processes and M resources types where N<10 && M<10

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;


public class BankersAlgorithm {
    private static int numberOfProcesses;
    private static int numberOfResources;
    private static int mainCrt =0;
    private static int complete =0;
    private static int []initial; 
    private static int max[][];
    private static int allocate[][];
    private static int need[][];
    private static int [] allocatedResources;
    private static int [] ttlResources;
    private static int [] holdInitial;
    private static int [] processSeq;

    private static Boolean multiSolution = true;
    private static Boolean firstTimeThru = true;
    private static Boolean []finished;
    private static Boolean []knownSolutions;
	

	/*
	 * how the file is going to look like   *
	 *  											   
	 *  5  # processes								   
     *  4  # recourses  						       
     *  | max | |alloc.| |ttl res                      *
     *  3,2,2,2,2,1,1,0,5,6,8,4                        *
     *  2,1,1,2,0,1,1,0,5,6,8,4                        *
     *  1,1,3,1,1,1,1,0,5,6,8,4                        *
     *  3,4,2,2,1,1,2,1,5,6,8,4                        *
     *  2,4,1,4,1,2,1,1,5,6,8,4                        *
  */
	
	public void setupFile() throws NumberFormatException, IOException {
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║        Banker's Algorithm      ║");
        System.out.println("╚════════════════════════════════╝");

		    Scanner ec = new Scanner(System.in);
        String fileName;
        System.out.println("Enter in the file name which is on this path");
        fileName = ec.nextLine();

        FileReader diskIn = new FileReader(fileName+".txt");
        BufferedReader inFile = new BufferedReader(diskIn);
        StringTokenizer tok;
        String rec;
        System.out.println();

        int i=0;
        int p =0;

        numberOfProcesses = Integer.parseInt(inFile.readLine());
        numberOfResources = Integer.parseInt(inFile.readLine());


        initial = new int[numberOfResources];
        holdInitial = new int[numberOfResources];
        max = new int[numberOfProcesses][numberOfResources];
        allocate = new int[numberOfProcesses][numberOfResources];
        need = new int[numberOfProcesses][numberOfResources];
        finished = new Boolean[numberOfProcesses];
        knownSolutions = new Boolean[numberOfProcesses];
        allocatedResources = new int[numberOfResources];
        ttlResources = new int [numberOfResources];
        processSeq = new int[numberOfProcesses];

        while((rec = inFile.readLine()) != null && i < numberOfProcesses){
            tok = new StringTokenizer(rec,",");
            for(int j=0;j<numberOfResources;j++) {
              max[i][j] = Integer.parseInt(tok.nextToken());   
            }

            for(int j=0;j<numberOfResources;j++) {
              allocate[i][j] = Integer.parseInt(tok.nextToken());
              need[i][j] = max[i][j] - allocate[i][j];   
            }

            for(int j=0;j<numberOfResources;j++) {
              ttlResources[j] = Integer.parseInt(tok.nextToken());
            }

            i++;
    }
    inFile.close();
        int process =0;
        //Used to load are allocatedResources array
        while(process < numberOfResources) {

          for(int j=0;j<numberOfProcesses;j++) {
        	  allocatedResources[p] += allocate[j][p]; 


          }
          p++;
          process++;
        }

        // used to load initial and holdIntial array
        for(int j=0;j<ttlResources.length;j++) {
        	initial[j] = ttlResources[j] - allocatedResources[j];
        	holdInitial[j] = initial[j];

        }

        //  used to load finished and knowSolutions array
        for(int a=0;a<numberOfProcesses;a++) {
          finished[a] = false;
          knownSolutions[a] = false;
        }

	}

  //  checkSafeSeq() checks to see if there is a 
  //  safe sequence. It does this by checking to
  //  see if need[i] <= initial[i]. 
	public boolean checkSafeSeq() {

		int crt = 0;
		int i   = 0;

		while(crt != numberOfResources && i < numberOfProcesses) {
			crt = 0;
		for(int j=0;j<numberOfResources;j++) {
			if(need[i][j] <= initial[j]) {
				crt++;
			}

		   }
		    i++;
		}

		if(i < numberOfProcesses)
			return true;

		return false;
	}

	//printMatrix() displays the content of the file.
	//It takes the max,allocate, and need array's
	//and creates string duplicates for display
	//purposes only.
	public void printMatrix() {
		String maxStr     = "";
		String allStr     = "";
		String needStr    = "";
		String ttlReStr   = "";
		String initialStr = "";
		String allReStr   = "";
		
		System.out.println("Pn  Max Claim   Allocation    Need");
		System.out.println("==================================");
		
		for(int j=0;j<numberOfProcesses;j++) {
			for(int k=0;k<numberOfResources;k++) {
				maxStr += Integer.toString(max[j][k]);
				allStr += Integer.toString(allocate[j][k]);
				needStr += Integer.toString(need[j][k]);

			}
			System.out.printf("P%d %6s  %11s  %10s",j,maxStr,allStr,needStr);

			maxStr ="";
			allStr = "";
			needStr = "";

			System.out.println();
		}
		
		for(int j=0;j<initial.length;j++) {
			ttlReStr += Integer.toString(ttlResources[j])+",";
		    allReStr += Integer.toString(allocatedResources[j])+",";
		    initialStr += Integer.toString(initial[j])+",";
		}

		System.out.println("\nTotal number of resources: ("+ttlReStr.substring(0,ttlReStr.length()-1)+")");
		System.out.println("Total number of Allocated resources: ("+allReStr.substring(0,allReStr.length()-1)+")");
		System.out.println("Total number of resources available in this initial state is: ("+initialStr.substring(0,initialStr.length()-1)+")");
		System.out.println();
		System.out.println();
	}
    /* 
     *  check() takes in a N(int i) and
     *  searches to see if need[N] <= allocate[N].
     *  
     *  It also takes a look to see if there
     *  are multiples solutions.
     */
	public Boolean check(int i) {
		
		String intStr = "";
		String needStr = "";
	
      for(int k=0;k<numberOfResources;k++) {
        intStr += initial[k];
        needStr += need[i][k];
			}

			if(Integer.parseInt(needStr)<= Integer.parseInt(intStr)) {
				if(firstTimeThru && !knownSolutions[i]) {  
					knownSolutions[i] = true;
					firstTimeThru = false;
					return true;
				}else if(!firstTimeThru && !knownSolutions[i]){				
					return true;
				}else if(!firstTimeThru && knownSolutions[i]){  
					return true;
				}

			}
		return false;
	}

  //algorithm() is the method that handles 
  //Bankers Algorithm.
	public void algorithm() throws NumberFormatException, IOException {
		int timesThruLoop =0;
	 
    while(complete < numberOfProcesses && multiSolution) {
      int i =0;
      for(i=0;i<numberOfProcesses;i++) {
          if(check(i) && !finished[i]) {
            System.out.print("P"+(i)+" released all that it holds. Available is: ");

            for(int j=0;j<numberOfResources;j++) {
            initial[j] = (initial[j] - need[i][j]) + max[i][j];
            System.out.print(initial[j]+",");
            }
            System.out.println();

            complete++;
            finished[i] = true;
            processSeq[complete-1] = i;
          }
      }

          timesThruLoop++;
          if(timesThruLoop == numberOfProcesses) 
            multiSolution = false;  
    }
    if(multiSolution) {
    System.out.print("\nSafe Sequence: < ");
    for(int j=0;j<processSeq.length;j++) {
      if(j != processSeq.length-1)
          System.out.print("P"+processSeq[j]+",");
      else
        System.out.print("P"+processSeq[j]);
    }
    System.out.print(" >\n");
    }   

    complete =0;
    for(int j=0;j<finished.length;j++) {
      finished[j] = false;
    }
    
    for(int j=0;j<initial.length;j++)
      initial[j] = holdInitial[j];

    mainCrt++;
    firstTimeThru = true;

    System.out.println();
	}

    public static void main(String[] args) throws IOException {
        BankersAlgorithm bank = new BankersAlgorithm();
        bank.setupFile();

       if(!bank.checkSafeSeq()) {
    	   System.out.println("There is no safe Sequence!");
    	   return;
       }

        bank.printMatrix();
        while(mainCrt < numberOfProcesses && multiSolution) {
        bank.algorithm();
        }
    }
}
