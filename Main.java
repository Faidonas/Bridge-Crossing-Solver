import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<FamilyMember> Members = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Write the number of members: ");
        int numberOfMembers = scanner.nextInt();

        int[] crossingTimes = new int[numberOfMembers];
        String[] MemberNames = new String[numberOfMembers];

        for (int i = 0; i < numberOfMembers; i++) {
            do{
                System.out.println("Write the speed of member " + (i + 1) + " that is needed to cross the river: ");
            try{
                crossingTimes[i] = scanner.nextInt();
                break;
                
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid integer.");
                scanner.nextLine();
            }
            }while(true);
          
            
            scanner.nextLine();
            System.out.println("Write the name of the member: ");
            MemberNames[i] = scanner.nextLine();
        }
        scanner.close();
    
        for (int i = 0; i <numberOfMembers; i++){
            FamilyMember Member = new FamilyMember(MemberNames[i], crossingTimes[i]);
            Members.add(Member);
        }
            
        State firstState = new State();
        firstState.setLeftSide(Members);

        Astar searcher = new Astar();
        long start = System.currentTimeMillis();
        
        State terminalState = searcher.astar_method(firstState);

        long end = System.currentTimeMillis();

        if(terminalState == null) System.out.println("Could not find a solution.");
        else
        {
			// print the path from beggining to start.
            State temp = terminalState; // begin from the end.
            ArrayList<State> path = new ArrayList<>();
			path.add(terminalState);
            while(temp.getFather() != null) // if father is null, then we are at the root.
            {
                path.add(temp.getFather());
                temp = temp.getFather();
            }
			// reverse the path and print.
            Collections.reverse(path);
            for(State item: path)
            {
                item.print(item);
            }
            System.out.println();
            System.out.println("Search time:" + (double)(end - start) / 1000 + " sec.");  // total time of searching in seconds.
    }
}
}