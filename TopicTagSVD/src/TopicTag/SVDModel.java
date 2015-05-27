package TopicTag;

import org.apache.commons.math3.linear.*;

public class SVDModel {
	RealMatrix matrix;
	SingularValueDecomposition svd;
	
	public SVDModel(RealMatrix m)
	{
		matrix = m;
		svd = new SingularValueDecomposition(matrix);
	}
	
	public RealMatrix returnU(int f)
	{
		RealMatrix Umat = svd.getU();
		if(f > Umat.getColumnDimension())
			return Umat;
		RealMatrix newU = Umat.getSubMatrix(0, Umat.getRowDimension()-1, 0, f-1);
		return newU;
	}
	
	public RealMatrix returnV(int f)
	{
		RealMatrix Vmat = svd.getV();
		if(f > Vmat.getColumnDimension())
			return Vmat;
		RealMatrix newV = Vmat.getSubMatrix(0, Vmat.getRowDimension()-1, 0, f-1);
		return newV;
	}
	
	public RealMatrix returnS(int f)
	{
		RealMatrix Smat = svd.getS();
		if(f > Smat.getColumnDimension())
			return Smat;
		RealMatrix newS = Smat.getSubMatrix(0, f-1, 0, f-1);
		return newS;
	}
}
