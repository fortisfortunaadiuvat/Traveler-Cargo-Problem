/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelercargoproblem; 
 
//import java.util.Scanner;

import java.util.ArrayList; 
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;


/**
 *
 * @author hakan
 */
public class TravelerCargoProblem {
    
    static ArrayList<String> Cities_List = new ArrayList<>();
    
    static boolean[][] platearray = new boolean[81][81];
    
    static int infinity = 9999;
    static int total_distance = 0;
    static int permutation = 1;
    
    static class BubbleSort { 
        void bubbleSort(int arr[],String arr2[][]) { 
            int n = arr.length; 
            
            for (int i = 0; i < n-1; i++){
                for (int j = 0; j < n-i-1; j++){
                    if (arr[j] > arr[j+1]) { 
                        // swap arr[j+1] and arr[i] 
                        int temp = arr[j];
                        String temp2[] = arr2[j];
                        
                        arr[j] = arr[j+1];
                        arr2[j] = arr2[j+1];
                        
                        arr[j+1] = temp;
                        arr2[j] = temp2;
                    } 
                }
            } 
                 
        }
        
        void printArray(int arr[],String arr2[][]) {  
            
            for (int i=0; i<arr2.length; ++i){
                System.out.println(Arrays.toString(arr2[i]) + ": " + arr[i] ); 
            }            
        } 
    } 
    
    public static int Find_City_Plate(String name){
        
        for(int i=0;i<81;i++){
            String key = Cities.name[i];
            
            if(key == null ? name == null : key.equals(name)){
                return i+1;
            }
        }
        
        return 0;
    }

    public static int MinDistance(int distance[],boolean Set[] ){
        int min=Integer.MAX_VALUE,min_index=0;
	
        for(int i=0;i<81;i++){
            if(Set[i]==false&&distance[i]<=min){
                     min=distance[i];
                     min_index=i;
            }
			
        }
        
	return min_index;
    }
    
    public static int MinArrayDistance(int[] distance){
        int min_array = distance[0];
        int min_index = 0;
        
        for(int i=0;i<distance.length;i++){
            if(distance[i]<min_array){
                min_array = distance[i];
                min_index = i;
            }
        }
        
        return distance[min_index];
    }
    
    public static void  Find_Total_Distance(int distance[],int n,String name){
    
        for(int i=0;i<81;i++){
            if(Cities.name[i].equals(name)){
                total_distance += distance[i];
            }
        }  
}

    public static void Dijkstra(int graph[][],int source,String name){
	int[] distance = new int[81];//The output array.
	boolean[] Set = new boolean[81];
        //sptSet[i] will be true if vertex i is included in shortest 
        //path tree or shortest distance from src to i is finalized 
	
	for(int i=0;i<81;i++){
            distance[i]=infinity;
            Set[i]=false;
        }
        
	distance[source]=0;
	
	for(int j=0;j<81;j++){
		int d=MinDistance(distance,Set);
		
		Set[d]=true;
		
		for(int i=0;i<81;i++){
                    if (!Set[i] && graph[d][i] != 0 && distance[d] != infinity  
                                       && distance[d]+graph[d][i] < distance[i]){
                        distance[i] = distance[d] + graph[d][i];
                    } 
                }		 
        }      
        Find_Total_Distance(distance,81,name); 
    }
    
    public static int Fact(int num){
        
        for(int i=1;i<=num;i++){
            permutation *= i;
        }
        
        return permutation;
    }
    
    public static int getNextUnused(LinkedList<Integer> used, Integer current) {
        int unused = current != null ? current : 0;
        
        while (used.contains(unused)) {
            ++unused;
        }
    
        return unused;
    }
   
    public static String[][] getAllPermutations(String[] str) {
        LinkedList<Integer> current = new LinkedList<>();
        LinkedList<Integer[]> permutations = new LinkedList<>();

        int length = str.length;
        current.add(-1);

        while (!current.isEmpty()) {
            // increment from the last position.
            int position = Integer.MAX_VALUE;
            position = getNextUnused(current, current.pop() + 1);
        
            while (position >= length && !current.isEmpty()) {
                position = getNextUnused(current, current.pop() + 1);
            }
        
            if (position < length) {
                current.push(position);
            } else {
                break;
            }

            // fill with all available indexes.
            while (current.size() < length) {
                // find first unused index.
                int unused = getNextUnused(current, 0);

                current.push(unused);
            }
            
            // record result row.
            permutations.add(current.toArray(new Integer[0]));
        }

        // select the right String, based on the index-permutation done before.
        int numPermutations = permutations.size();
        String[][] result = new String[numPermutations][length];
        
        for (int i = 0; i < numPermutations; ++i) {
            Integer[] indexes = permutations.get(i);
            String[] row = new String[length];
            for (int d = 0; d < length; ++d) {
                row[d] = str[indexes[d]];
            }
            result[i] = row;
        }

    return result;
}
    
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scan = new Scanner(System.in);
        
        System.out.print("Please enter number of city that will have delivered cargo to: ");
        int number_of_cargo_city = scan.nextInt();
        
        String[] city = new String[number_of_cargo_city];
        
        System.out.println("Please enter the city name:");
        
        
        for(int i=0;i<number_of_cargo_city;i++){
            city[i] = scan.next();
        }
        
        String[][] array = getAllPermutations(city);
        
        int[] distance = new int[Fact(number_of_cargo_city)];
        
        for(int i=0;i<distance.length;i++){
            Dijkstra(Cities.distance,40,array[i][0]);
            
            for(int j=0;j<city.length-1;j++){
                Dijkstra(Cities.distance,Find_City_Plate(array[i][j])-1,array[i][j+1]);
            }
            Dijkstra(Cities.distance,40,array[i][number_of_cargo_city-1]);
            
            distance[i] = total_distance;
            
            total_distance = 0;
        }
        
        System.out.println("The shortest path: " + MinArrayDistance(distance));
        
        BubbleSort bs = new BubbleSort();
        bs.bubbleSort(distance,array);
        
        System.out.println("Sorted array: ");
        
        bs.printArray(distance,array);
                
    }
    
}
