package com.google.round1A2015;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class C {


    public static Comparator<C.Solver.PolarPoint> polarPointComparator = new Comparator<C.Solver.PolarPoint>() {
        public int compare(C.Solver.PolarPoint p1, C.Solver.PolarPoint p2) {
            return p2.getPolarAngle().compareTo(p1.getPolarAngle());
        }
    };

    public static Comparator<Set<Integer>> subgroupSorter = new Comparator<Set<Integer>>() {
        public int compare(Set<Integer> p1, Set<Integer> p2) {
            return new Integer(p1.size()).compareTo(new Integer(p2.size()));
        }
    };

    static class Solver implements Callable<Integer[]> {

        public class PolarPoint {

            private Double polarAngle;
            private Point point;

            public PolarPoint(Point point) {
                this(point, new Double("0"));
            }

            public PolarPoint(Point point, Double polarAngle) {
                this.point = point;
                this.polarAngle = polarAngle;
            }

            public Double getPolarAngle() {
                return polarAngle;
            }

            public Point getPoint() {
                return point;
            }
        }

        public class ConvexHull{
            List<Point> points;
            List<Line2D> edges;
            Point centroid;

            public ConvexHull(List<Point> points){
                this.points = points;
                this.edges = new LinkedList<Line2D>();
                int centroidX = 0, centroidY = 0;
                for(int i=1;i<points.size();i++){
                    edges.add(new Line2D.Double(points.get(i-1), points.get(i)));
                    centroidX += points.get(i).x;
                    centroidY += points.get(i).y;
                }
                centroidX = (centroidX / points.size());
                centroidY = (centroidY / points.size());
                centroid = new Point(centroidX, centroidY);
            }

            public boolean contains(Point p){
                if(points.contains(p)){
                    return true;
                }
                for(Line2D l : edges){
                    if(l.ptSegDist(p.x, p.y) == 0){
                        return true;
                    }
                }
                return false;
            }

            public List<Point> getPoints(){
                return points;
            }

            public Point getCentroid(){
                return centroid;
            }
        }

        private void populateArray(Integer[] o){
            for (int i = 0;i<o.length; i++){
                o[i] = new Integer(i);
            }

        }

        private Set<Set<Integer>> getIndexSubGroups(int size){
            Set<Set<Integer>> subGroups = new HashSet<Set<Integer>>();
            try{
                Integer[] indexes = new Integer[size];
                populateArray(indexes);
                getSubsets(indexes, subGroups, 1);
                for(Integer index : indexes){
                    Set<Integer> temp = new HashSet<Integer>();
                    temp.add(index);
                    subGroups.remove(temp);
                }
            }catch(NumberFormatException nfe){
                nfe.printStackTrace(System.err);
            }
            return subGroups;

        }

        private void getSubsets(Integer[] indexes, Set<Set<Integer>> subGroups, int n){
            for (int i = 0;i<indexes.length ;i++ ){
                Set<Integer> s = new HashSet<Integer>();
                if(n==1){
                    s.add(indexes[i]);
                    subGroups.add(s);
                }else if(n>1){
                    if(n>indexes.length)
                        return;
                    generateHigherOrderSubSets(subGroups, n, indexes);
                }
            }
            getSubsets(indexes, subGroups, ++n);
        }

        private void generateHigherOrderSubSets(Set<Set<Integer>> subGroups, int n, Integer[] o){
            List<Set<Integer>> list1 = getList(subGroups, 1);
            List<Set<Integer>> list2 = getList(subGroups, n-1);
            for (int x = 0;x<list2.size() ;x++ ){
                for (int y= 0;y<list1.size() ;y++ ){
                    Set<Integer> s = new HashSet<Integer>();
                    if(!getListOfObjectsFromASetContainedInAList(list2, x).contains(getListOfObjectsFromASetContainedInAList(list1, y).get(0))){
                        List<Integer> l1 = getListOfObjectsFromASetContainedInAList(list2, x);
                        l1.add(getListOfObjectsFromASetContainedInAList(list1, y).get(0));
                        for (int z=0;z<l1.size() ;z++ )
                            s.add(l1.get(z));
                        if(!subGroups.contains(s))
                            subGroups.add(s);
                    }
                }
            }
        }

        private List<Integer> getListOfObjectsFromASetContainedInAList(List<Set<Integer>> list, int x){
            Integer[] subGroup = list.get(x).toArray(new Integer[0]);
            List<Integer> l = new ArrayList<Integer>();
            for (int i = 0 ;i<subGroup.length ;i++ )
                l.add(subGroup[i]);
            return l;
        }

        private List<Set<Integer>> getList(Set<Set<Integer>> subGroups, int size){
            List<Set<Integer>> list = new ArrayList<Set<Integer>>();
            for (Iterator<Set<Integer>> iter = subGroups.iterator();iter.hasNext(); ){
                Set<Integer> s = iter.next();
                if(s.size()==size){
                    list.add(s);
                }
            }
            return list;
        }

        private ConvexHull GrahamScan(List<Point> points) {
            List<Point> hull = new LinkedList<Point>();
            // Find the minimum y-coord, in case of tie find leftmost point out of
            // those
            List<Point> allPoints = new LinkedList<Point>();
            allPoints.addAll(points);
            int indexOfMin = 0;
            Point minimum = allPoints.get(0);
            for (int i = 1; i < allPoints.size(); i++) {
                if (allPoints.get(i).y <= minimum.y) {
                    if (allPoints.get(i).y == minimum.y) {
                        if (allPoints.get(i).x < minimum.x) {
                            minimum = allPoints.get(i);
                            indexOfMin = i;
                        }
                    } else {
                        minimum = allPoints.get(i);
                        indexOfMin = i;
                    }
                }
            }

            /*
             * Sort the remaining points by polar-angle in counter-clockwise order
             * around 'leftMost', in case of two points having the same angle remove
             * all but the farthest from 'leftMost'.
             */
            allPoints.remove(indexOfMin);
            Map<Double, PolarPoint> polarAngles = new TreeMap<Double, PolarPoint>();
            for (Point p : allPoints) {
                Point vector = new Point(p.x - minimum.x, p.y - minimum.y);
                Double polarAngle = Math.atan2(vector.y, vector.x);
                if (polarAngles.containsKey(polarAngle)) {
                    if (minimum.distance(polarAngles.get(polarAngle).getPoint()) < minimum
                            .distance(p))
                        polarAngles.put(polarAngle, new PolarPoint(p, polarAngle));
                } else
                    polarAngles.put(polarAngle, new PolarPoint(p, polarAngle));
            }

            if(polarAngles.size() < 2){
                return new ConvexHull(points);
            }

            // Sort points in counterclockwise order (increasing polar angle)
            List<PolarPoint> polarPoints = new LinkedList<PolarPoint>();
            for (PolarPoint pp : polarAngles.values())
                polarPoints.add(pp);
            Collections.sort(polarPoints, polarPointComparator);
            polarPoints.add(0, new PolarPoint(minimum));

            Stack<Point> hullPoints = new Stack<Point>();
            hullPoints.push(polarPoints.get(0).getPoint());
            hullPoints.push(polarPoints.get(1).getPoint());
            hullPoints.push(polarPoints.get(2).getPoint());
            for (int i = 3; i < polarPoints.size(); i++) {
                Point head = polarPoints.get(i).getPoint();
                Point middle = hullPoints.pop();
                Point tail = hullPoints.pop();
                if (formsLeftTurn(tail, middle, head)) {
                    hullPoints.push(tail);
                    hullPoints.push(middle);
                    hullPoints.push(head);
                } else {
                    hullPoints.push(tail);
                    i--;
                }
            }
            hullPoints.push(polarPoints.get(0).getPoint());
            hull.addAll(hullPoints);
            return new ConvexHull(hull);
        }

        private boolean formsLeftTurn(Point a, Point b, Point c) {
            return (((new Double(a.x) - new Double(b.x)) * (new Double(c.y) - new Double(b.y))) - ((new Double(c.x) - new Double(b.x)) * (new Double(a.y) - new Double(b.y)))) >= 0;
        }

        Point[] points;

        Solver(Point[] points){
            this.points = points;
        }

        @Override
        public Integer[] call() throws Exception {

            int N = points.length;
            Integer[] cuts = new Integer[N];
            Arrays.fill(cuts, 0);

            if(N > 3) {
                ConvexHull hull = GrahamScan(Arrays.asList(points));
                for (int n = 0; n < N; n++) {
                    if (hull.contains(points[n])) {
                        cuts[n] = 0;
                    } else {
                        int curr_cuts = 0;

                        List<Point> other_points = new LinkedList<Point>();
                        for (int n2 = 0; n2 < N; n2++) {
                            if (n2 != n) {
                                other_points.add(points[n2]);
                            }
                        }

                        Set<Set<Integer>> subgroups = getIndexSubGroups(other_points.size());
                        boolean exit = false;
                        for (int j = N - 1; j > 3; j--) {
                            curr_cuts = N - (j + 1);
                            for (Set<Integer> subgroup : subgroups) {
                                if (subgroup.size() == j) {
                                    List<Point> tmp_points = new LinkedList<Point>();
                                    tmp_points.add(points[n]);
                                    for (Integer i : subgroup) {
                                        tmp_points.add(other_points.get(i));
                                    }
                                    ConvexHull tmp_hull = GrahamScan(tmp_points);
                                    if (tmp_hull.contains(points[n])) {
                                        exit = true;
                                        break;
                                    }
                                }
                            }
                            if (exit) break;
                        }

                        cuts[n] = curr_cuts;
                    }
                }
            }
            return cuts;
        }
    }

    public static void main(String[] args) throws Exception{
        Scanner scan = new Scanner(System.in);
        BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\aschonfe\\Desktop\\tmp.out"));
        String firstLine = scan.nextLine();
        Boolean isFile = !Character.isDigit(firstLine.charAt(0));
        int T;
        if(isFile){
            scan = new Scanner(new FileReader(firstLine));
            T = scan.nextInt();
        }else{
            T = Integer.parseInt(firstLine);
        }

        ScheduledThreadPoolExecutor service = new ScheduledThreadPoolExecutor(7);
        Future<Integer[]>[] ts = new Future[T];
        for(int t=0;t<T;t++){
            int N = scan.nextInt();
            Point[] points = new Point[N];
            int minX = 0;
            int minY = 0;
            for(int n=0;n<N;n++){
                points[n] = new Point(scan.nextInt(), scan.nextInt());
                if(points[n].x < minX){
                    minX = points[n].x;
                }
                if(points[n].y < minY){
                    minY = points[n].y;
                }
            }
            //update points to center around 0,0
//            for(Point p : points){
//                p.move(p.x + Math.abs(minX), p.y + Math.abs(minY));
//            }

            ts[t] = service.submit(new Solver(points));
        }

        for (int t=0; t<T; ++t) {
            while (!ts[t].isDone()) {
                Thread.sleep(500);
            }
            System.out.printf("Case #%d:%n", (t+1));
            out.write(String.format("Case #%d:%n", (t+1)));
            for (int cut : ts[t].get()) {
                System.out.printf("%d%n", cut);
                out.write(String.format("%d%n", cut));
            }
        }
        if(isFile){
            scan.close();
        }
        out.close();
    }
}

