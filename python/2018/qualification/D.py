import math
import numpy as np
from scipy.spatial import ConvexHull


class Node:
    def __init__(self, coordinates):
        self.x = coordinates[0]
        self.y = coordinates[1]
        self.z = coordinates[2]


class Edge:
    def __init__(self, start, stop):
        self.start = start
        self.stop  = stop


class Wireframe:
    def __init__(self):
        self.nodes = []
        self.edges = []

    def addNodes(self, nodeList):
        for node in nodeList:
            self.nodes.append(Node(node))

    def addEdges(self, edgeList):
        for (start, stop) in edgeList:
            self.edges.append(Edge(self.nodes[start], self.nodes[stop]))

    def buildSides(self):
        node_dict = {'{}{}{}'.format(n.x, n.y, n.z): i for i, n in enumerate(self.nodes)}
        self.side1 = [node_dict[n] for n in ['000', '100', '110', '010']]
        #self.side2 = [node_dict[n] for n in ['000', '001', '101', '100']]
        self.side2 = [node_dict[n] for n in ['001', '011', '111', '101']]
        self.side3 = [node_dict[n] for n in ['000', '001', '011', '010']]


    def sideCentroids(self):

        def centroid(nodes, side):
            return sum(nodes[i].x for i in side) / 4.0, sum(nodes[i].y for i in side) / 4.0, sum(nodes[i].z for i in side) / 4.0
        return [centroid(self.nodes, self.side1), centroid(self.nodes, self.side2), centroid(self.nodes, self.side3)]


    def outputNodes(self):
    	print "\n --- Nodes --- "
        for i, node in enumerate(self.nodes):
            print " %d: (%.2f, %.2f, %.2f)" % (i, node.x, node.y, node.z)

    def outputEdges(self):
    	print "\n --- Edges --- "
        for i, edge in enumerate(self.edges):
            print " %d: (%.2f, %.2f, %.2f)" % (i, edge.start.x, edge.start.y, edge.start.z),
            print "to (%.2f, %.2f, %.2f)" % (edge.stop.x,  edge.stop.y,  edge.stop.z)

    def translate(self, axis, d):
        """ Add constant 'd' to the coordinate 'axis' of each node of a wireframe """

        if axis in ['x', 'y', 'z']:
            for node in self.nodes:
                setattr(node, axis, getattr(node, axis) + d)

    def scale(self, (centre_x, centre_y), scale):
        """ Scale the wireframe from the centre of the screen """

        for node in self.nodes:
            node.x = centre_x + scale * (node.x - centre_x)
            node.y = centre_y + scale * (node.y - centre_y)
            node.z *= scale

    def findCentre(self):
        """ Find the centre of the wireframe. """

        num_nodes = len(self.nodes)
        meanX = sum([node.x for node in self.nodes]) / num_nodes
        meanY = sum([node.y for node in self.nodes]) / num_nodes
        meanZ = sum([node.z for node in self.nodes]) / num_nodes

        return (meanX, meanY, meanZ)

    def rotateX(self, (cx,cy,cz), radians):
        for node in self.nodes:
            y      = node.y - cy
            z      = node.z - cz
            d      = math.hypot(y, z)
            theta  = math.atan2(y, z) + radians
            node.z = cz + d * math.cos(theta)
            node.y = cy + d * math.sin(theta)

    def rotateY(self, (cx,cy,cz), radians):
        for node in self.nodes:
            x      = node.x - cx
            z      = node.z - cz
            d      = math.hypot(x, z)
            theta  = math.atan2(x, z) + radians
            node.z = cz + d * math.cos(theta)
            node.x = cx + d * math.sin(theta)

    def rotateZ(self, (cx,cy,cz), radians):
        for node in self.nodes:
            x      = node.x - cx
            y      = node.y - cy
            d      = math.hypot(y, x)
            theta  = math.atan2(y, x) + radians
            node.x = cx + d * math.cos(theta)
            node.y = cy + d * math.sin(theta)

def PolygonArea(corners):
    n = len(corners) # of corners
    area = 0.0
    for i in range(n):
        j = (i + 1) % n
        area += corners[i][0] * corners[j][1]
        area -= corners[j][0] * corners[i][1]
    area = abs(area) / 2.0
    return area


if __name__ == "__main__":
    cube_nodes = [(x,y,z) for x in (0,1) for y in (0,1) for z in (0,1)]
    cube = Wireframe()
    cube.addNodes(cube_nodes)
    cube.addEdges([(n,n+4) for n in range(0,4)])
    cube.addEdges([(n,n+1) for n in range(0,8,2)])
    cube.addEdges([(n,n+2) for n in (0,1,4,5)])
    cube.buildSides()
    for x, y, z in cube.sideCentroids():
        print x, y, z

    cube.rotateZ((0,0,0), 45 * (math.pi / 180))
    pts = np.array([np.array([node.x, node.y]) for node in cube.nodes])
    ch = ConvexHull(pts)
    print PolygonArea(pts[ch.vertices])

    # for node in [node for i, node in enumerate(cube.nodes) if i in [4, 2, 1]][::-1]:
    #     #print "%.16f, %.16f, %.16f" % (node.x, node.y, node.z)
    #     print node.x, node.y, node.z
    for x, y, z in cube.sideCentroids():
        print x, y, z

    #cube.rotateX(cube.findCentre(), 45 * (math.pi / 180))
    #cube.outputNodes()
    #cube.outputEdges()

# def run_test():
#     pass
#
# T = int(raw_input())
# for t in range(1, T+1):
#     print("Case #{}: {}".format(t, run_test()))

# with open('A.out', "w+") as f:
#     for i in range(1, int(raw_input()) + 1):
#         f.write("Case #{}: {}\n".format(i, run_test()))
