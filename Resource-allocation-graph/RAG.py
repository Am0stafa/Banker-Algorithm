import argparse
import networkx as nx
import matplotlib.pyplot as plt

class Process:
    def __init__(self, name, priority):
        self.name = name
        self.priority = priority
        self.alloc = {}  # Allocated resources
        self.req = {}    # Requested resources

class Resource:
    def __init__(self, name, instances):
        self.name = name
        self.instances = instances
        self.alloc = {}  # Allocated instances

class ResourceAllocationGraph:
    def __init__(self):
        self.processes = {}
        self.resources = {}

    def add_process(self, name, priority):
        if name not in self.processes:
            self.processes[name] = Process(name, priority)
        else:
            raise ValueError(f"Process {name} already exists")

    def add_resource(self, name, instances):
        if name not in self.resources:
            self.resources[name] = Resource(name, instances)
        else:
            raise ValueError(f"Resource {name} already exists")

    def allocate(self, resource_name, process_name, instances):
        if resource_name in self.resources and process_name in self.processes:
            resource = self.resources[resource_name]
            process = self.processes[process_name]
            if resource.instances >= instances:
                resource.instances -= instances
                resource.alloc[process_name] = instances
                process.alloc[resource_name] = instances
            else:
                raise ValueError(f"Insufficient instances of {resource_name}")
        else:
            raise ValueError(f"Invalid resource {resource_name} or process {process_name}")

    def release(self, resource_name, process_name, instances):
        if resource_name in self.resources and process_name in self.processes:
            resource = self.resources[resource_name]
            process = self.processes[process_name]
            if process_name in resource.alloc and resource.alloc[process_name] >= instances:
                resource.instances += instances
                resource.alloc[process_name] -= instances
                process.alloc[resource_name] -= instances
                if resource.alloc[process_name] == 0:
                    del resource.alloc[process_name]
                    del process.alloc[resource_name]
            else:
                raise ValueError(f"No allocation of {resource_name} to {process_name} found")
        else:
            raise ValueError(f"Invalid resource {resource_name} or process {process_name}")

    def visualize(self):
        G = nx.DiGraph()
        for process_name, process in self.processes.items():
            G.add_node(process_name, color='red')
            for resource_name, instances in process.alloc.items():
                G.add_edge(process_name, resource_name, weight=instances)
        for resource_name, resource in self.resources.items():
            G.add_node(resource_name, color='blue')
            for process_name, instances in resource.alloc.items():
                G.add_edge(resource_name, process_name, weight=instances)
        colors = [node[1]['color'] for node in G.nodes(data=True)]
        weights = [edge[2]['weight'] for edge in G.edges(data=True)]
        pos = nx.spring_layout(G)
        nx.draw(G, pos, node_color=colors, with_labels=True)
        nx.draw_networkx_edge_labels(G, pos, edge_labels={(u, v): f'{d["weight"]}' for u, v, d in G.edges(data=True)})
        plt.show()
        if nx.is_directed_acyclic_graph(G):
            print("No cycle detected!")
        else:
            print("Cycle detected!")

def main():
    parser = argparse.ArgumentParser(description='Resource Allocation Graph')

    parser.add_argument('--processes', nargs='+', type=str, help='List of processes and priorities in the format ProcessName:Priority')
    parser.add_argument('--resources', nargs='+', type=str, help='List of resources and instances in the format ResourceName:Instances')
    parser.add_argument('--allocate', nargs='+', type=str, help='Allocation requests in the format ProcessName:ResourceName:Instances')
    parser.add_argument('--release', nargs='+', type=str, help='Resource release requests in the format ProcessName:ResourceName:Instances')

    args = parser.parse_args()

    rag = ResourceAllocationGraph()

    for process in args.processes:
        name, priority = process.split(':')
        priority = int(priority)
        rag.add_process(name, priority)

    for resource in args.resources:
        name, instances = resource.split(':')
        instances = int(instances)
        rag.add_resource(name, instances)

    for allocation in args.allocate:
        process_name, resource_name, instances = allocation.split(':')
        instances = int(instances)
        rag.allocate(resource_name, process_name, instances)

    for release in args.release:
        process_name, resource_name, instances = release.split(':')
        instances = int(instances)
        rag.release(resource_name, process_name, instances)

    rag.visualize()

if __name__ == "__main__":
    main()
