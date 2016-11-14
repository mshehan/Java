/////////-----------------------------------------------------------------------------
// Name: Matthew Shehan
// CruzID: 1499521
// Pa3
// Matrix.java
// A sparse Matrix ADT
//-----------------------------------------------------------------------------
class Matrix{

  class Entry{
    // Fields
    private int col;
    private double val;

    // Constructors
    Entry(int c, double v){
      col = c;
      val = v;
    }
    // Other Functions
    public boolean equals(Object x){
      boolean eq = false;
      if(x instanceof Entry){
        Entry that = (Entry)x;
        if(this != that){
          eq = ((this.col == that.col) && (this.val == that.val));
        }
      }
      return eq;
    }
    public String toString(){
      return "(" + col + "," + val + ")";
    }
  }
  // Fields
  private List[] row;
  private int size;

  //Constructor
  Matrix(int n){
    if(n < 1){
      throw new RuntimeException(
          "Matrix Error: Matrix() called with negative number");
    }
    row = new List[n+1];
    for(int i = 1; i <= n; i++){
      row[i] = new List();
    }
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
    for(int i=1; i <= this.getSize(); i++) {
      nzSize += this.row[i].length();
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
      Matrix that = (Matrix)x;
      if(this != that) {
        eq = ((this.getSize() == that.getSize())
            && this.getNNZ() == that.getNNZ());
        for( int i=1; eq && i < this.getSize(); i++) {
          eq = row[i].equals(that.row[i]);
          if(!eq)return eq;
        }
      }else{
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
        Q.moveNext();
      }else if(Ep.col < Eq.col){
        P.moveNext();
      }else{
        dotProduct += (Ep.val * Eq.val);
        P.moveNext();
        Q.moveNext();
      }
    }
    return dotProduct;
  }
  /*void makeZero()
   * sets this Matrix to the zero state
   */
  void makeZero(){
    for(int i=1; i < this.getSize(); i++){
      row[i].clear();
    }
  }

  /*Matrix copy()
   * returns a new Matrix having the same entries as this Matrix
   */
  Matrix copy(){
    Matrix copy = new Matrix(this.getSize());
    for(int i=1; i <= this.getSize(); i++){
      this.row[i].moveFront();
      while(this.row[i].index() != -1){
        Entry e = (Entry)this.row[i].get();
        copy.changeEntry(i,e.col,e.val);
        this.row[i].moveNext();
      }
    }
    return copy;
  }

  /*void changeEntry(int i, int j, double x)
   * changes ith row, jth column of this Matrix to x
   * pre: 1<=i<=getSize(), 1<=j<=getSize()
   */
  void changeEntry(int i, int j, double x){
    if((1 > i && i>this.getSize()) || (1>j && j>this.getSize())){
      throw new RuntimeException(
          "Matrix Error: changeEntry called on invalid entry");
    }
    Entry e = null;
    boolean exists = false;
    this.row[i].moveFront();

    while(this.row[i].index() != -1){
      e = (Entry)this.row[i].get();
      if(e.col == j){
        exists = true;
        if(Double.compare(x,0) != 0){
          e.val = x;
          return;
        }
        if(Double.compare(x,0) == 0){
          this.row[i].delete();
          return;
        }
      }
      this.row[i].moveNext();
    }
    this.row[i].moveFront();
    if((!exists) && (Double.compare(x,0) != 0)){
      while((this.row[i].index()!=-1) &&
          (((Entry)this.row[i].get()).col < j)){
        this.row[i].moveNext();
      }
      if(this.row[i].index() != -1){
        this.row[i].insertBefore(new Entry(j,x));
      }else {
        this.row[i].append(new Entry(j,x));
      }
    }else{
      return;
    }
  }

  /*scalarMult(double x)
   * returns a new Matrix that is the scalar product of this Matrix with M
   */
  Matrix scalarMult(double x){
    Entry e = null;
    Matrix mult = this.copy();
    for(int i=1; i <= mult.getSize(); i++){
      mult.row[i].moveFront();
      while(mult.row[i].index()!=-1){
        e = (Entry)mult.row[i].get();
        e.val *= x;
        mult.row[i].moveNext();
      }
    }
    return mult;
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
// returning empty matrix
// edit else statement
    Matrix sum = new Matrix(this.getSize());
    Entry e1 = null;
    Entry e2 = null;
    if(this == M){
      sum = this.copy().scalarMult(2);
      return sum;
    }

    for(int i=1; i <= this.getSize(); i++){
      this.row[i].moveFront();
      M.row[i].moveFront();
      while(row[i].index()!=-1 || M.row[i].index()!=-1){
        //if index e is assigned else no entries
        //hasE set to true or false
        if(this.row[i].index()!= -1 && M.row[i].index()!=-1){
          e1 = (Entry)this.row[i].get();
          e2 = (Entry)M.row[i].get();
          if(e1.col == e2.col){
            sum.changeEntry(i,e1.col,(e1.val+e2.val));
            this.row[i].moveNext();
            M.row[i].moveNext();
          }
          if(e1.col > e2.col){
            sum.changeEntry(i,e2.col,e2.val);
            M.row[i].moveNext();
          }
          if(e1.col < e2.col){
            sum.changeEntry(i,e1.col,e1.val);
            this.row[i].moveNext();
          }
        }
        if(M.row[i].index()==-1 && this.row[i].index()!=-1){
          e1 = (Entry)this.row[i].get();
          sum.changeEntry(i,e1.col,e1.val);
          this.row[i].moveNext();
        }
        if(this.row[i].index()==-1 && M.row[i].index()!=-1){
          e2 = (Entry)M.row[i].get();
          sum.changeEntry(i,e2.col,e2.val);
          M.row[i].moveNext();
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
    Matrix sub = new Matrix(this.getSize());
    Entry e1 = null;
    Entry e2 = null;
    if(this == M){
      return sub;
    }

    for(int i=1; i <= this.getSize(); i++){
      this.row[i].moveFront();
      M.row[i].moveFront();
      while(row[i].index()!=-1 || M.row[i].index()!=-1){
        //if index e is assigned else no entries
        //hasE set to true or false
        if(this.row[i].index()!= -1 && M.row[i].index()!=-1){
          e1 = (Entry)this.row[i].get();
          e2 = (Entry)M.row[i].get();
          if(e1.col == e2.col){
            sub.changeEntry(i,e1.col,(e1.val-e2.val));
            this.row[i].moveNext();
            M.row[i].moveNext();
          }
          if(e1.col > e2.col){
            sub.changeEntry(i,e2.col,-(e2.val));
            M.row[i].moveNext();
          }
          if(e1.col < e2.col){
            sub.changeEntry(i,e1.col,e1.val);
            this.row[i].moveNext();
          }
        }
        if(M.row[i].index()==-1 && this.row[i].index()!=-1){
          e1 = (Entry)this.row[i].get();
          sub.changeEntry(i,e1.col,e1.val);
          this.row[i].moveNext();
        }
        if(this.row[i].index()==-1 && M.row[i].index()!=-1){
          e2 = (Entry)M.row[i].get();
          sub.changeEntry(i,e2.col,-(e2.val));
          M.row[i].moveNext();
        }
      }
    }
    return sub;
  }
  /*Matrix Transpose()
   * returns a new Matrix that is the transpose of this Matrix
   */
  Matrix transpose(){
    Matrix transpose = new Matrix(this.getSize());
    for(int i=1; i <= this.getSize(); i++){
      Entry e = null;
      this.row[i].moveFront();
      while(this.row[i].index()!=-1){
        e = (Entry)this.row[i].get();
        transpose.changeEntry(e.col,i,e.val);
        this.row[i].moveNext();
      }
    }
    return transpose;
  }
  /*Matrix mult(Matrix M)
   * returns a new Matrix that is the product of this Matrix
   * pre: getSize() == M.getSize()
   */
  Matrix mult(Matrix M){
    if(this.getSize() != M.getSize()){
      throw new RuntimeException(
          "Matrix Error: mult() called on matrices of uneven size");
    }
    Matrix product = null;
    Matrix Mtran = null;
    product = new Matrix(this.getSize());
    Mtran = M.transpose();
    for(int i=1; i <= this.getSize(); i++){
      if(this.row[i].length()==0)continue;
      for(int j = 1; j <= this.getSize(); j++){
        if(Mtran.row[i].length()==0)continue;
        product.changeEntry(i,j,dot(this.row[i],Mtran.row[j]));
      }
    }
    return product;
  }
/*------------------------Other Functions--------------------*/
  /*public String toString()
   * overrides Object's toString() method
   */
  public String toString(){
    String M = "";
    for(int i=1; i <= this.getSize(); i++){
      if(row[i].length() > 0){
        M += (i + ":" + row[i] + "\n");
      }
    }
    return M;
  }
}
