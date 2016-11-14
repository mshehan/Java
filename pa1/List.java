//-----------------------------------------------------------------------------
// Name: Matthew Shehan
// CruzID: 1499521
// Pa1
// List.java
// An integer List ADT
//-----------------------------------------------------------------------------

class List{

  private class Node{

    // Fields
    private int data;
    private Node previous;
    private Node next;


    // Constructor
    Node(int data) {
      this.data = data;
      previous = next = null;
    }

    // toString()
    // Overrides Object's toString() method.
    public String toString() {
      return String.valueOf(data);
    }

    // equals()
    // Overrides Object's equals() method.
    public boolean equals(Object x){
      boolean eq = false;
      Node that;
      if(x instanceof Node){
        that = (Node)x;
        eq = (this.data==that.data);
      }
      return eq;
    }
  }

   // Fields
  private Node front;
  private Node back;
  private Node cursor;
  private int length;

  // Constructor
  List() {
    front = back = cursor = null;
    length = 0;
  }


   // Access Functions --------------------------------------------------------

  /*
  // length()
  // Returns length of List.
  */
  int length() {
    return length;
  }
   /*
   // index()
   //if cursor is defined returns the index of the cursor element,
   // otherwise returns -1
   */
  int index() {
    int index = -1;
    Node current = front;
    if(cursor != null){
      index = 0;
      while( current != cursor) {
        current = current.next;
        index ++;
      }
    }
    return index;
  }
   /*
   // front()
   // Returns the front element.
   // Pre: length() > 0
   */
  int front() {
    if( length() <= 0 ){
      throw new RuntimeException(
        "List Error: front() called  on empty List");
    }
    return front.data;
  }
   /*
   //back()
   //returns back element
   //pre: length() > 0
   */
  int back() {
    if( length() <= 0 ){
      throw new RuntimeException(
        "List Error: back() called  on empty List");
    }
    return back.data;
  }
   /*
   //get()
   //returns cursor element
   // pre: length() > 0, index() >= 0
   */
  int get() {
    if(length() <= 0 && index() < 0) {
      throw new RuntimeException(
        "List Error: get() called on empty List");
    }
    return cursor.data;
  }
   /*
   //Returns true if this List and L are the same integer
   // sequence. the cursor is ignored in both lists.
   */
  boolean equals(List L) {
    boolean equal = false;
    Node N , M;
    N = this.front;
    M = L.front;
    equal = (this.length() == L.length());
    while(equal && N != null) {
      equal = N.equals(M);
      N = N.next;
      M = M.next;
    }
    return equal;
  }
   // Manipulation Procedures -------------------------------------------------

   /*
   //clear()
   //returns this list to its original empty state
   */
  void clear() {
    front = back = cursor = null;
    length = 0;
  }
  void moveFront() {
    if(length <= 0) {
      throw new RuntimeException(
        "List Error: moveback() called on empty List");
    }
    cursor = front;
  }
   /*
   //moveback()
   //if List is non-empty, places the cursor under the back element,
   //otherwise does nothing
   */
  void moveBack() {
    if(length <= 0) {
      throw new RuntimeException(
        "List Error: moveback() called on empty List");
    }
    cursor = back;
  }

   /*
   //movePrev()
   //if curosor is defined and not at front, moves cursorone step towards
   //front of this List, if curosor is defined at front, cursor becomes
   //undefined, if cursor is undefined does nothing
   */
  void movePrev() {
    if(cursor != null) {
      cursor = cursor.previous;
    }
  }
   /*
   moveNext()
   //if cursor is defined and not at back, moves cursor one step
   //toward back of this List, if cursor is defined and at back,
   //cursor becomes undefined, if cursor is undefined does nothing.
   */
  void moveNext() {
    if(cursor != null) {
      cursor = cursor.next;
    }
  }

   /*
   //prepend(int data)
   // insert new element into this List. If List is non-empty,
   // insertion takes place before front element.
   */
  void prepend (int data) {
    Node insert = new Node(data);
    if(length() == 0) {
      front = back = insert;
    } else {
      front.previous = insert;
      insert.next = front;
      front = insert;
    }
    length++;
  }
   /*
   //append(int data)
   //insert new element into this List. if List is non-empty,
   //insertion takes place after back element.
   */
  void append (int data) {
    Node insert = new Node(data);
    if(length() == 0) {
      front = back = insert;
    } else {
      back.next = insert;
      insert.previous = back;
      back = insert;
    }
    length++;
  }
   /*
   //insert new element before curosr.
   //pre: length() > 0, index() >= 0
   */
   void insertBefore(int data) {
    if (length() <= 0 || index() < 0) {
      throw new RuntimeException(
        "List Error: insertBefore() called on invalid List");
    }

    Node insert = new Node(data);
    if(cursor != null) {
    	if(cursor.previous == null)
    	{
    		prepend(data);
    	} else {
    	  insert.next = cursor;
        insert.previous = cursor.previous;
        cursor.previous.next = insert;
        cursor.previous = insert;
        length++;
    	}
       }
   }
   /*
   //insertAfter(int data)
   //inserts new element after cursor.
   //pre: length() > 0, index() > 0
   */
   void insertAfter(int data) {
    if (length() <= 0 || index() < 0) {
      throw new RuntimeException(
        "List Error: insertBefore() called on invalid List");
    }
    Node insert = new Node(data);
    if(cursor != null) {
    	if(cursor.next == null) {
    		append(data);
    	} else {
    		insert.previous = cursor;
        insert.next = cursor.next;
        cursor.next.previous = insert;
        cursor.next = insert;
    	}
    }
  }
   /*
   //deleteFront()
   //Deletes the front element
   //Pre: length() > 0
   */
  void deleteFront() {
    if(length() <= 0){
    	throw new RuntimeException(
    		"List Error: deleteFront() called on empty list");
    }
    front = front.next;
    front.previous.next = null;
    front.previous = null;
    length--;
  }
   /*
   //deleteBack()
   //Deletes the back element.
   //pre:length() > 0
   */
  void deleteBack() {
    back = back.previous;
    back.next.previous = null;
    back.next = null;
    length--;
  }
   /*
   //delete()
   //deletes cursor element, making cursor undefined.
   //pre: length() > 0, index >= 0
   */
  void delete() {
    if(length() <= 0 || index() < 0){
    throw new RuntimeException(
      "List Error: delete() called on undefined cursor");
    }
    if(cursor == front){
      deleteFront();
    }
    if(cursor == back){
      deleteBack()
    }
    if(cursor!=front || cursor!=back){
      cursor.previous.next = cursor.next;
      cursor.next.previous = cursor.previous;
      length--;
    }

    cursor = null;
  }
   // Other Functions ---------------------------------------------------------

   /*
   // toString()
   // Overrides Object's toString() method.
   */
  public String toString(){
    StringBuffer sb = new StringBuffer();
    Node N = front;
    while( N!=null ){
      sb.append(N.toString());
      sb.append(" ");
      N = N.next;
    }
    return new String(sb);
  }
   /*
    //copy()
    // returns a new List representing the same integer sequence as this.
    //List. The cursor in the new list is undefined, regardless of the
    //state of the cursor in this List. This List is unchanged.
    */
  List copy() {
    List copy = new List();
    if(this.length() > 0) {
      Node iterator = front;
      while(iterator != null) {
        copy.append(iterator.data);
        iterator = iterator.next;
      }
    }

    return copy;
  }
   /*
   //concat(List L)
   //returns a new List which is the concatenation of
   // this list folled by L. The cursor in the new List
   // in this List and L. The states of this List and L are
   //unchanged.
   */
  List concat(List L) {
    List concatList = new List();
    if(L.length() > 0 || this.length() > 0){
      concatList = L.copy();
      Node iterator = this.front;
      while(iterator != null) {
        concatList.append(iterator.data);
        iterator = iterator.next;
      }
    }
       return concatList;
   }
}
