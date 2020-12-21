package api;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.Test;

class DWGraph_DSTest {
	
	static directed_weighted_graph graph;
	@Test
	void testgETnODE() {
		int n = (int)(Math.random()*10)+1;
		int m =(int)(Math.random()*10)+1;
		int t=(int)(Math.random()*10)+1;
		graph = new DWGraph_DS();
		graph.addNode(new NodeData(n));
		graph.addNode(new NodeData(m));
		graph.addNode(new NodeData(t));
		assertEquals(graph.getNode(n).getKey(),n);
		assertEquals(m, graph.getNode(m).getKey());
		assertEquals(t, graph.getNode(t).getKey());
	}
	
	@Test
	void testGetEdge() 
	{
		int n = (int)(Math.random()*10)+1;
		int m =(int)(Math.random()*10)+1;
		int t=(int)(Math.random()*10)+1;

		graph = new DWGraph_DS();
		graph.addNode(new NodeData(0));
		graph.addNode(new NodeData(1));
		graph.addNode(new NodeData(2));
		graph.addNode(new NodeData(3));

		graph.connect(0, 1, n);
		graph.connect(1, 2, m);
		graph.connect(2, 3, t);
         
		edge_data e0 = new EdgeData(0, 1, n);
		edge_data e1 = new EdgeData(1, 2, m);
		edge_data e2 = new EdgeData(2, 3, t);
		
		assertEquals(graph.getEdge(0, 1),e0 );
		assertEquals(graph.getEdge(1, 2), e1);
		assertEquals(graph.getEdge(2, 3), e2);
	}
	
	@Test
	void testAddNode() 
	{
		int n = 0;
		int n1 = 1;
		int n2 =2;

		graph = new DWGraph_DS();
		graph.addNode(new NodeData(0));
		graph.addNode(new NodeData(1));
		graph.addNode(new NodeData(2));

		assertEquals(graph.getNode(n).getKey(), n);
		assertEquals(graph.getNode(n1).getKey(), n1);
		assertEquals(graph.getNode(n2).getKey(), n2);
	}

	@Test
	void testConnect() 
	{
		int p = (int)(Math.random()*10)+1;
		int m =(int)(Math.random()*10)+1;
		int t=(int)(Math.random()*10)+1;

		int n =0;
		int n1 =1;
		int n2 =2;
		int n3 =3;

		graph = new DWGraph_DS();
		graph.addNode(new NodeData(0));
		graph.addNode(new NodeData(1));
		graph.addNode(new NodeData(2));
		graph.addNode(new NodeData(3));

		graph.connect(n, n1, p);
		graph.connect(n, n2, m);
		graph.connect(n, n3, t);
        int sumOfConnect = 3;
		assertEquals(graph.getE(0).size(), sumOfConnect);
	}

	@Test
	void testGetV() 
	{
		Collection<node_data> nodes = graph.getV();
		int nodeSize = graph.nodeSize();
		assertEquals(nodeSize, nodes.size());
	}

	@Test
	void testGetVNi() 
	{
		int p = (int)(Math.random()*10)+1;
		int m =(int)(Math.random()*10)+1;
		int t=(int)(Math.random()*10)+1;

		int n =0;
		int n1 =1;
		int n2 =2;
		int n3 =3;

		graph = new DWGraph_DS();
		graph.addNode(new NodeData(0));
		graph.addNode(new NodeData(1));
		graph.addNode(new NodeData(2));
		graph.addNode(new NodeData(3));

		graph.connect(n, n1, p);
		graph.connect(n1, n2, m);
		graph.connect(n2, n3, t);


		int NiSize = 0;


		Collection<edge_data> Ni = graph.getE(0);
		for (edge_data node1: Ni) {
			if(node1 != null) 
			{
				NiSize++;
			}
		}
		assertEquals(graph.getE(0).size(), NiSize);
	}

	@Test
	void testRemoveNode() 
	{
		int p = (int)(Math.random()*10)+1;
		int m =(int)(Math.random()*10)+1;
		int t=(int)(Math.random()*10)+1;

		int n =0;
		int n1 =1;
		int n2 =2;
		int n3 =3;

		graph = new DWGraph_DS();
		graph.addNode(new NodeData(0));
		graph.addNode(new NodeData(1));
		graph.addNode(new NodeData(2));
		graph.addNode(new NodeData(3));
		int before = graph.nodeSize();

		graph.connect(n, n1, p);
		graph.connect(n1, n2, m);
		graph.connect(n2, n3, t);

		graph.removeNode(n);
		assertFalse(graph.getV().contains(graph.getNode(n)));
		assertEquals(graph.nodeSize(), before-1);
	}

	@Test
	void testRemoveEdge() 
	{
		int p = (int)(Math.random()*10)+1;
		int m =(int)(Math.random()*10)+1;
		int t=(int)(Math.random()*10)+1;

		int n =0;
		int n1 =1;
		int n2 =2;
		int n3 =3;

		graph = new DWGraph_DS();
		graph.addNode(new NodeData(0));
		graph.addNode(new NodeData(1));
		graph.addNode(new NodeData(2));
		graph.addNode(new NodeData(3));


		graph.connect(n, n1, p);
		graph.connect(n1, n2, m);
		graph.connect(n2, n3, t);

		int edges_befor = graph.edgeSize();

		graph.removeEdge(n, n1);
		int edges_after = graph.edgeSize();

		assertEquals(edges_befor-1, edges_after);
	}
	
	
	private directed_weighted_graph buildGraph() {
		directed_weighted_graph graph = new DWGraph_DS();
		graph.addNode(new NodeData(0));
		graph.addNode(new NodeData(1));
		graph.addNode(new NodeData(2));
		graph.addNode(new NodeData(3));
		graph.addNode(new NodeData(4));
		graph.addNode(new NodeData(5));
		graph.addNode(new NodeData(6));
		graph.connect(0, 1, 1);
		graph.connect(1, 0, 9);
		graph.connect(1, 2, 2);
		graph.connect(2, 1, 10);
		graph.connect(2, 3, 3);
		graph.connect(3, 2, 11);
		graph.connect(3, 4, 4);
		graph.connect(4, 3, 1);
		graph.connect(4, 5, 5);
		graph.connect(5, 4, 13);
		graph.connect(5, 0, 6);
		graph.connect(0, 5, 14);
		graph.connect(0, 1, 1);
		graph.connect(1, 6, 7);
		graph.connect(6, 1, 15);
		graph.connect(6, 4, 8);
		graph.connect(4, 6, 16);
		return graph;
	}

}
