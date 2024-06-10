import java.util.*;

public class State implements Comparable<State> {
	private int f, h, g;
	private State father;
	private int totalTime;
	private ArrayList<FamilyMember> LeftSide;
	private ArrayList<FamilyMember> RightSide;
	private boolean torch;
	private String names = "";
	private String names2 = "";

	// Constructor
	public State(ArrayList<FamilyMember> LeftSide, ArrayList<FamilyMember> RightSide, int totalTime, State father,
			int f, int h,
			int g) {

		this.LeftSide = LeftSide;
		this.RightSide = RightSide;
		this.f = f;
		this.h = h;
		this.g = g;
		this.father = father;
		this.totalTime = totalTime;
		this.torch = true;
	}

	// Default constructor

	public State() {

		this.LeftSide = new ArrayList<FamilyMember>();
		this.RightSide = new ArrayList<FamilyMember>();
		this.f = 0;
		this.h = 0;
		this.g = 0;
		this.father = null;
		this.totalTime = 0;
		this.torch = true;

	}

	// Copy constructor
	public State(State s) {

		this.LeftSide = new ArrayList<>(s.LeftSide);
		this.RightSide = new ArrayList<>(s.RightSide);
		this.f = s.f;
		this.h = s.h;
		this.g = s.g;
		this.father = s.father;
		this.totalTime = s.totalTime;
	}

	public void setLeftSide(ArrayList<FamilyMember> leftside){
		this.LeftSide = leftside;
	}

	public ArrayList<FamilyMember> getLeftSide(){
		return this.LeftSide;
	}

	public ArrayList<FamilyMember> getRightSide(){
		return this.RightSide;
	}

	public int getF() {
		return this.f;
	}

	public int getG() {
		return this.g;
	}

	public int getH() {
		return this.h;
	}

	public State getFather() {
		return this.father;
	}

	public void setF(int f) {
		this.f = f;
	}

	public void setG(int g) {
		this.g = g;
	}

	public void setH(int h) {
		this.h = h;
	}

	public void setFather(State f) {
		this.father = f;
	}

	public int getTotalTime() {
		return this.totalTime;
	}

	public void setTotalTime(int time) {
		this.totalTime = time;
	}

	private boolean isTorch() {
		return torch;
	}

	public void evaluate() 
	{
		int fscore = getH() + getG();
		setF(fscore);//Calculate the f score of the State
	}

	public ArrayList<State> getChildren() {
		ArrayList<State> children = new ArrayList<State>();
		if (this.torch)//If the torch is LeftSide
			MoveLeftToRight(children);
		else//If the torch is RightSide
			MoveRightToLeft(children);
		return children;
	}

	private void MoveLeftToRight(ArrayList<State> children){
		if (this.LeftSide.size()>1){ // if there are more than 1 runners on the left side
			cross2LeftToRight(children);//cross 2 runners from left to right
			cross1LeftToRight(children);//cross 1 runner from left to right
		}else//if there is only one runner
			cross1LeftToRight(children);//cross 1 runner left to right
	}

	private void MoveRightToLeft(ArrayList<State> children){
		if (this.RightSide.size()>1){// if there are more than 1 runners on the right side
			cross2RightToLeft(children);//cross 2 runners from right to left
			cross1RightToLeft(children);//cross 1 runner from right to left
		}
		else//if there is only one runner
			cross1RightToLeft(children);//cross 1 runner from right to left
	}

	private void cross1LeftToRight(ArrayList<State> children){
		// Iterate through the persons on the LeftSide
		for (int i = 0; i < this.LeftSide.size(); i++) {
			// Create a new State instance as a temporary state
			State temp = new State(this);
			temp.setFather(this);
			temp.setG(this.g + 1);
		
			// Move a single person from LeftSide to RightSide in the temporary state
			temp.getRightSide().add(temp.getLeftSide().get(i));
		
			// Update total time and heuristic for the temporary state
			temp.setTotalTime(temp.getTotalTime() + temp.getLeftSide().get(i).time);
			temp.setH(temp.getH() + temp.getLeftSide().get(i).time);
		
			// Remove the moved person from the temporary state's LeftSide
			temp.getLeftSide().remove(i);
	
			// Toggle the torch position in the temporary state
			temp.torch = !this.isTorch();
		
			// Evaluate the heuristic value for the temporary state
			temp.evaluate();
		
			// Add the temporary state to the list of children states
			children.add(temp);
			}
		}
		
	private void cross2LeftToRight(ArrayList<State> children) {
		// Iterate through the persons on the LeftSide
		for (int i = 0; i < this.LeftSide.size(); i++) {
			// Iterate through the remaining persons on the LeftSide
			for (int j = i + 1; j < this.LeftSide.size(); j++) {
				// Create a new State instance as a temporary state
				State temp = new State(this);
				temp.setFather(this);
				temp.setG(this.g + 1);

				// Move persons from LeftSide to RightSide in the temporary state
				temp.getRightSide().add(temp.getLeftSide().get(i));
				temp.getRightSide().add(temp.getLeftSide().get(j));

				// Calculate total time and heuristic for the temporary state
				int speedI = temp.getLeftSide().get(i).time;
				int speedJ = temp.getLeftSide().get(j).time;
				temp.setTotalTime(temp.getTotalTime() + ((speedI <= speedJ) ? speedJ : speedI));
				temp.setH(temp.getH() + ((speedI <= speedJ) ? speedJ : speedI));

				// Remove moved persons from the temporary state's LeftSide
				temp.getLeftSide().remove(j);
				temp.getLeftSide().remove(i);

				// Toggle the torch position in the temporary state
				temp.torch = !this.torch;

				// Evaluate the heuristic value for the temporary state
				temp.evaluate();

				// Add the temporary state to the list of children states
				children.add(temp);
			}
		}
	}


	private void cross1RightToLeft(ArrayList<State> children) {
		// Iterate through the persons on the RightSide
		for (int i = 0; i < this.RightSide.size(); i++) {
			// Create a new State instance as a temporary state
			State temp = new State(this);
			temp.setFather(this);
			temp.setG(this.g + 1);
	
			// Move a single person from RightSide to LeftSide in the temporary state
			temp.getLeftSide().add(temp.getRightSide().get(i));
	
			// Update total time and heuristic for the temporary state
			temp.setTotalTime(temp.getTotalTime() + temp.getRightSide().get(i).time);
			temp.setH(temp.getH() + temp.getRightSide().get(i).time);
	
			// Remove the moved person from the temporary state's RightSide
			temp.getRightSide().remove(i);
	
			// Toggle the torch position in the temporary state
			temp.torch = !this.torch;
	
			// Evaluate the heuristic value for the temporary state
			temp.evaluate();
	
			// Add the temporary state to the list of children states
			children.add(temp);
		}
	
	}

	private void cross2RightToLeft(ArrayList<State> children) {
		 // Iterate through the persons on the RightSide
		 for (int i = 0; i < this.RightSide.size(); i++) {
			// Iterate through the remaining persons on the RightSide
			for (int j = i + 1; j < this.RightSide.size(); j++) {
				// Create a new State instance as a temporary state
				State temp = new State(this);
				temp.setFather(this);
				temp.setG(this.g + 1);
	
				// Move persons from RightSide to LeftSide in the temporary state
				temp.getLeftSide().add(temp.getRightSide().get(i));
				temp.getLeftSide().add(temp.getRightSide().get(j));
	
				// Determine speeds of the moved persons
				int speedI = temp.getRightSide().get(i).time;
				int speedJ = temp.getRightSide().get(j).time;
	
				// Update total time and heuristic for the temporary state
				temp.setTotalTime(temp.getTotalTime() + ((speedI <= speedJ) ? speedJ : speedI));
				temp.setH(temp.getH() + ((speedI <= speedJ) ? speedJ : speedI));
	
				// Remove moved persons from the temporary state's RightSide
				temp.getRightSide().remove(j);
				temp.getRightSide().remove(i);
	
				// Toggle the torch position in the temporary state
				temp.torch = !this.torch;
	
				// Evaluate the heuristic value for the temporary state
				temp.evaluate();
	
				// Add the temporary state to the list of children states
				children.add(temp);
			}
		}
	}


	public boolean isFinal() {
		if (this.LeftSide.size() == 0)//If the LeftSide is Empty return true
			return true;
		else
			return false;	
	}

	public boolean checkEqualityOfLists(ArrayList<FamilyMember> firstList, ArrayList<FamilyMember> secondList) {
		// Check if both lists are null
		if (firstList == null && secondList == null)
			return true;
		
		// Check if only one of the lists is null
		if ((firstList == null && secondList != null) || (firstList != null && secondList == null))
			return false;
		
		// Check if the lists have different sizes
		if (firstList.size() != secondList.size())
			return false;
		
		// Iterate through elements of the first list
		for (Object itemInFirstList : firstList) {
			// Check if the second list contains the current element
			if (!secondList.contains(itemInFirstList))
				return false;
			
			// Check if the frequency of the current element is the same in both lists
			if (Collections.frequency(firstList, itemInFirstList) != Collections.frequency(secondList, itemInFirstList))
				return false;
		}
		
		// If all checks pass, the lists are considered equal
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
	
		// Check if the object is null
		if (obj == null) {
			return false;
		}
	
		// Check if the object is not an instance of State
		if (!State.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
	
		// Cast the object to State
		final State other = (State) obj;
	
		// Check equality of the LeftSide lists
		if (!checkEqualityOfLists(this.LeftSide, other.getLeftSide())) {
			return false;
		}
	
		// Check equality of the RightSide lists
		if (!checkEqualityOfLists(this.RightSide, other.getRightSide())) {
			return false;
		}
	
		// Check if the torch states are equal
		if (this.isTorch() != other.isTorch()) {
			return false;
		}
	
		// If all checks pass, the objects are considered equal
		return true;
	}

	@Override
	public int hashCode() {
		// The hash code is calculated based on the sum of the values of 'f', the size of LeftSide, and the size of RightSide
		return this.f + this.getLeftSide().size() + this.getRightSide().size();
	}	

	@Override
	public int compareTo(State s) {
		return Double.compare(this.f, s.getF()); // compare based on the heuristic score.
	}

	public void print(State s) {
		for (FamilyMember i : s.LeftSide) {
			names += i.name + ", ";
		}
		for (FamilyMember i : s.RightSide) {
			names2 += i.name + ", ";
		}
		System.out.printf("Left side: %s%nRight side: %s%nTotal Time: %d%n",
				names, names2, totalTime);

		if (isTorch()==true)
			System.out.println("Torch is LeftSide\n");
		else
			System.out.println("Torch is RightSide\n");
	}
}