package game.sniper_monkey.utils.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/**
 * An Xml utility used to convert NodeLists to an iterable collection.
 * <p>
 * Used by MapReader.
 * Used by TileReader.
 *
 * @author -
 */
public final class NodeListUtil {
    private NodeListUtil() {
    }

    /**
     * Converts a NodeList to a List of nodes.
     *
     * @param nodeList The NodeList to be converted
     * @return A List of Nodes
     */
    public static List<Node> asList(NodeList nodeList) {
        return nodeList.getLength() == 0 ? Collections.emptyList() : new NodeListWrapper(nodeList);
    }

    private static final class NodeListWrapper extends AbstractList<Node> implements RandomAccess {
        private final NodeList list;

        NodeListWrapper(NodeList l) {
            list = l;
        }

        public Node get(int index) {
            return list.item(index);
        }

        public int size() {
            return list.getLength();
        }
    }
}
