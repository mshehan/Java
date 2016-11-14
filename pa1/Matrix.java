class Matrix{

  class Entry{
    // Fields
    private int col;
    private double val;

    // Constructors
    Entry(c,v){
      colum = c;
      val = v;
    }

    // Other Functions
    public boolean equals(Entry e){
      return ((e.col == this.col) && (e.val == this.val));
    }
    private toString(){
      String.toString("(" + col + "," + val + ")");
    }
  }
  // Fields
  private List[] row;
  private int size;

  //Constructor
  Matrix(int n){
    row = new List[n];
    size = n;
  }
  /*------------------Access Functions-------------------*/

  /* int getSize()
   * returns n, the number of rows and columns of this Matrix
   */
  int getSize(){
    return size;
  }

  /*int getNNZ()
   * returns the number of non-zero entries in this Matrix*/
  int getNNZ(){
    int nzSize = 0;
    for(int i = 1; i < n; i++) {
      nzsize += row[i].length();
    }
    return nzSize;
  }

  /* public boolean equals(Object x)
   * overrides object's equals() method
   * compares two Maxtrix objects
   */
  public boolean equals(Object x){
    boolean eq = false;
    if(x instanceof Matrix){
      that = (Matrix)x
      if(this != x) {
        eq = ((this.getSize() == x.getSize())
            && this.getNNZ() == x.getNNZ());
        for( int i = 1; eq && i < this.getSize() ; i++){
          eq = row[i].equals(that.row[i]);
        }
      }
      else {
        eq = true;
      }
    }

    return eq;
  }

  /*-------------------Manipulation Procedures-------------------*/
  /*private static double dot(List P, List Q)
   * computs the dot product of two matrix rows represented by Lists P and Q*/
  private static double dot(List P, List Q){
    double dotProduct = 0;
    P.moveFront();
    Q.moveFront();
    while(P.index()!=-1 && Q.index()!=-1){
      Entry Ep = (Entry)P.get();
      Entry Eq = (Entry)Q.get();
      if(Ep.col > Eq.col){
        Eq.moveNext();
      }else if(Ep.col < Eq.col){
        Ep.moveNext();
      }else{
        dotProduct += (Ep.val * Eq.val);
        Eq.moveNext();
        Ep.moveNext();
      }
    }
    return dotProduct;
  }
  /*void makeZero()
   * sets this Matrix to the zero state
   */
  void makeZero(){
    for(int i = 1; i < n; i++){
      row[i].clear();
    }
  }

  /*Matrix copy()
   * returns a new Matrix having the same entries as this Matrix
   */
  Matrix copy(){
    Matrix copy = new Matrix(this.getSize());
    for(int i = 1; i < this.getSize(); i++){
      this.row[i].moveFront();
      while(this.row[i].index() != -1){
        copy.row[i].append((Entry)this.row[i].get());
      }
    }
    return copy;
  }

  /*void changeEntry(int i, int j, double x)
   * changes ith row, jth column of this Matrix to x
   * pre: 1<=i<=getSize(), 1<=j<=getSize()
   */
  void changeEntry(int i, int j, double x){
    if(1 < i && i>getSize() || 1 < j && j > getSize()){
      throw new RuntimeException(
          "Matrix Error: changeEntry called on invalid entry");
    }

    Entry e; //e is a pointer to entry objects held by list
    row[i].moveFront();
    if(x == 0){
        while(row[i].index() != -1){
          e = (Entry)row[i].get();
          if(e.col == j){
            row[i].delete();
          }
          //else do nothing
          row[i].moveNext();
        }
    }
    if(x != 0) {
      //used to check weather the column exists
      boolean exists = false;
      //iterate through the whole thing and search
      while(row[i].index() != -1){
        e = (Entry)row[i].get();
        if(e.col == j){
          e.val = x;
          exists = true;
        }
        row[i].moveNext();
      }
      //column not found so put it in the right place
      if(!exists){
        row[i].moveFront();
        while(row[i].index() != -1){
          e = (Entry)row[i].get();
          if(e.col < j){
            row[i].moveNext();
          }else{
            row[i].insertBefore(new Entry(j,x));
          }
        }
      }
    }
  }

  /*scalarMult(double x)
   * returns a new Matrix that is the scalar product of this Matrix with M
   */
  Matrix scalarMult(double x){
    boolean hasEntry;
    Entry e;
    for(int i = 1; i < this.getSize(); i++){
      //move the cursor to the front of the list
      this.row[i].moveFront();
      hasEntry = (this.row[i].index() != -1)?true:false;
      while(hasEntry){
        e.val *= x;
        this.row[i].moveNext();
        hasEntry = (this.row[i].index() != -1)?true:false;
      }
    }
  }

  /*Matrix add(Matrix M)
   * returns a new Matrix that is the sum of this Matrix with M
   * pre: getSize() == M.getSize()
   */
  Matrix add(Matrix M){
    if(this.getSize() != M.getSize()){
      throw new RuntimeException(
          "Matrix Error: add called on matrices of uneven size");
    }

    Matrix sum;
    boolean hasE1;
    boolean hasE2;
    Entry e1;
    Entry e2;
    if(this == M){
      sum = this.scalarMult(2);
    }else{
      for(int i = 1; i < this.getSize(); i++){
        while(row[i].index()!=-1 || M.row[i].index()!=-1){
          //if index e is assigned else no entries
          //hasE set to true or false
          (row[i].index()!=-1)?(e1 = row[i].get()),hasE1=true
            :hasE1=false;

          (M.row[i].index()!=-1)?(e2 = M.row[i].get(),hasE2=true)
            :hasE2=false;

          case (hasE1 && hasE2):{
            if(e1.col < e2.col){
              sum.changeEntry(i,e1.col,e1.val);
              this.row[i].moveNext();
            }else if(e1.col > e2.col){
              sum.changeEntry(i,e2.col,e1.val);
              M.row[i].moveNext();
            }else{
              sum.changeEntry(i,e1.col,(e1.val+e2.val));
              this.row[i].moveNext();
              M.row[i].moveNext();
            }
            break;
          }
          case (hasE1 && !hasE2):{
             sum.row[i].append(e1);
             this.row[i].moveNext();
             break;
          }
          case (!hasE1 && hasE2):{
             sum.row[i].append(e2);
             M.row[i].moveNext();
             break;
          }
        }
      }
    }
    return sum;
  }

  /*Matrix sub(Matrix M)
   * returns a new Matrix that is the difference of this Matrix with M
   * pre: getSize() == M.getSize()
   */
  Matrix sub(Matrix M){
    if(this.getSize() != M.getSize()){
      throw new RuntimeException(
          "Matrix Error: sub() called on matrices of different size");
    }

    Matrix sub;
    if(this == M){
      sub = new Matrix(this.getSize());
    }else{
      leftHS = M.scalarMult(-1.0);
      sub = this.add(leftHS);
    }
    return sub;
  }
  /*Matrix Transpose()
   * returns a new Matrix that is the transpose of this Matrix
   */
  Matrix transpose(){
    Matrix transpose = new Matrix(this.getSize());
    for(int i = 1; i < this.getSize(); i++){
      Entry e;
      this.row[i].moveFront();
      while(this.row[i].index()!=-1){
        e = (Entry)this.row[i].get();
        transpose.changeEntry(e.col,i,e.val);
        this.row[i].moveNext();
      }
    }
  }
  /*Matrix mult(Matrix M)
   * returns a new Matrix that is the product of this Matrix
   * pre: getSize() == M.getSize()
   */
  Matrix mult(Matrix M){
    if(this.getSize != M.getSize()){
      throw new RuntimeException(
          "Matrix Error: mult() called on matrices of uneven size");
    }
    Matrix product = new Matrix(this.getSize());
    Matrix colums = M.transpose();
    for(int i = 1; i < this.getSize(); i++){
      if(this.row[i].length()==0)continue;
      for(int j = 0; j < this.getSize(); i++){
        if(columns.row[j].lenght()==0)continue;
        product.changeEntry(i,j,dot(this.row[i],columns.row[j]);
      }
    }
  }
/*------------------------Other Functions--------------------*/
  /*public String toString()
   * overrides Object's toString() method
   */
  public String toString(){
    String M;
    for(int i = 1; i < this.getSize(); i++){
      this.row[i].moveFront();
      while(this.row[i].index()!=-1){
        M += (Entry)this.row[i].get().toString();
      }
    }
  }
}
