/////////-----------------------------------------------------------------------------
// Name: Matthew Shehan
// CruzID: 1499521
// Pa3
// List.java
// An Object List ADT
//-----------------------------------------------------------------------------

class List{

  private class Node{

    // Fields
    private Object data;
    private Node previous;
    private Node next;


    // Constructor
    Node(Object data) {
      this.data = data;
      previous = next = null;
    }

    // toString()
    // Overrides Object's toString() method.
    public String toString() {
      return data.toString();
    }

    // equals()
    // Overrides Object's equals() method.
    public boolean equals(Object x){
      boolean eq = false;
      Node that;
      if(x instanceof Node){
        that = (Node)x;
        eq = (this.data.equals(that.data)); // must deref and comp both Objects
      }
      return eq;
    }
  }

   // Fields
  private Node front;
  private Node back;
  private Node cursor;
  private int length;
  private int index;
  // Constructor
  List() {
    front = back = cursor = null;
    length = 0;
    index = -1;
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
    return index;
  }
   /*
   // front()
   // Returns the front element.
   // Pre: length() > 0
   */
  Object front() {
    if( length() <= 0 ){
      throw new RuntimeException(
        "List Error: front() called  on empty List");
    }
    return front.data; //returns an obeject stored in node
  }
   /*
   //back()
   //returns back element
   //pre: length() > 0
   */
  Object back() {
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
  Object get() {
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
  public boolean equals(List L) {
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
    index = -1;
  }
   /*
   //moveFront()
   //if List is non-empty, places the cursor under the front element,
   //otherwise does nothing
   */
  void moveFront() {
    if(this.length() >= 1){
      cursor = front;
      index = 0;
    }
  }
   /*
   //moveback()
   //if List is non-empty, places the cursor under the back element,
   //otherwise does nothing
   */
  void moveBack() {
    if(this.length() >= 1){
      cursor = back;
      index = this.length() - 1;
    }
  }
   /*
   //movePrev()
   //if curosor is defined and not at front, moves cursorone step towards
   //front of this List, if curosor is defined at front, cursor becomes
   //undefined, if cursor is undefined does nothing
   */
  void movePrev() {
    if(cursor == front) {
      cursor = null;
      index = -1;
    }
    if(cursor !=null && cursor != front){
      cursor = cursor.previous;
      index--;
    }
  }
   /*
   moveNext()
   //if cursor is defined and not at back, moves cursor one step
   //toward back of this List, if cursor is defined and at back,
   //cursor becomes undefined, if cursor is undefined does nothing.
   */
  void moveNext() {
    if(cursor == back){
      cursor = null;
      index = -1;
    }
    if(cursor != null && cursor != back) {
      cursor = cursor.next;
      index++;
    }
  }

   /*
   //prepend(Object data)
   // insert new element Objecto this List. If List is non-empty,
   // insertion takes place before front element.
   */
  void prepend (Object data) {
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
   //append(Object data)
   //insert new element Objecto this List. if List is non-empty,
   //insertion takes place after back element.
   */
  void append (Object data) {
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
   void insertBefore(Object data) {
    if (length() <= 0 || index() < 0) {
      throw new RuntimeException(
        "List Error: insertBefore() called on invalid List");
    }

    Node insert = new Node(data);
    if(cursor != null) {
    	if(cursor.previous == null){
    		prepend(data);
        index++;
        return;
    	} else {
    	  insert.next = cursor;
        insert.previous = cursor.previous;
        cursor.previous.next = insert;
        cursor.previous = insert;
        length++;
        index++;
    	}
    }
   }
   /*
   //insertAfter(Object data)
   //inserts new element after cursor.
   //pre: length() > 0, index() > 0
   */
   void insertAfter(Object data) {
    if (length() <= 0 || index() < 0) {
      throw new RuntimeException(
        "List Error: insertAfter() called on invalid List");
    }
    Node insert = new Node(data);
    if(cursor != null) {
    	if(cursor.next == null) {
    		append(data);
        index--;
    	} else {
    		insert.previous = cursor;
        insert.next = cursor.next;
        cursor.next.previous = insert;
        cursor.next = insert;
        length++;
        index--;
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
    if(cursor == front){
      index = -1;
      cursor = null;
    }
    front = front.next;
    if(front != null){
      front.previous.next = null;
      front.previous = null;
      if(cursor > 0){
        index--;
      }
    }
    length--;
  }
   /*
   //deleteBack()
   //Deletes the back element.
   //pre:length() > 0
   */
  void deleteBack() {
    if(length() <= 0){
    	throw new RuntimeException(
    		"List Error: deleteBack() called on empty list");
    }
    if(cursor == back){
      index = -1;
      cursor = null;
    }
    back = back.previous;
    if(back != null){
      back = back.previous;
      back.next.previous = null;
      back.next = null;
    }
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
      return;
    }
    if(cursor == back){
      deleteBack();
      return;
    }

    cursor.previous.next = cursor.next;
    cursor.next.previous = cursor.previous;
    cursor = null;
    length--;
    index = -1;
  }
   // Other Functions ---------------------------------------------------------

   /*
   // toString()
   // Overrides Object's toString() method.
   */
  public String toString(){
    Node N = front;
    String printlist = "";
    while( N !=null ){
      printlist += N.toString() + " ";
      N = N.next;
    }
    return printlist;
  }

}
