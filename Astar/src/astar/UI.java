package astar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * 
 * @author Matias Leone
 */
public class UI {
 
    private final static int WIN_WIDTH = 1200;
    private final static int WIN_HEIGHT = 720;
    
    private enum RenderState {
        NORMAL,
        SELECTED,
        SOLUTION
    }
    
    private enum AppState {
        NORMAL,
        FIST_VERTEX_SELECTED,
        SOLVING,
        SOLVED
    }
    
    private JFrame frame;
    private JTextArea logArea;
    private RenderPanel renderPanel;
    private BufferedImage renderImg;
    private Graphics2D renderG;
    private Dimension graphDim;
    private List<VertexWidget> vertexWidgets;
    private List<EdgeWidget> edgeWidgets;
    private AppState state;
    private VertexWidget selectedVertexFrom;
    private VertexWidget selectedVertexTo;
    
    public static void main(String[] args) {
        new UI();
    }
    
    public UI() {
        state = AppState.NORMAL;
        initGraph();
        
        frame = new JFrame("A Star");
        frame.setMinimumSize(new Dimension(WIN_WIDTH, WIN_HEIGHT));
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        
        renderPanel = new RenderPanel();
        frame.add(renderPanel, BorderLayout.CENTER);
        
        logArea = new JTextArea(4, 100);
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setMinimumSize(new Dimension(-1, 200));
        frame.add(scrollPane, BorderLayout.SOUTH);
        
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenDim.width / 2 - WIN_WIDTH / 2, screenDim.height / 2 - WIN_HEIGHT / 2);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
        frame.setVisible(true);
    }
    
    private void initGraph() {
        vertexWidgets = new ArrayList<VertexWidget>();
        edgeWidgets = new ArrayList<EdgeWidget>();
        
        int vertIdx = 0;
        final int offsetX = 10;
        final int offsetY = 10;
        final int vertRad = 30;
        final int vertSep = 40;
        final int vertPerRow = 17;
        final int vertPerCol = 9;
        for (int i = 0; i < vertPerCol; i++) {
            for (int j = 0; j < vertPerRow; j++) {
                Vertex v = new Vertex(vertIdx++);
                VertexWidget vertWidget = new VertexWidget(v, offsetX + (vertRad + vertSep) * j, offsetY + (vertRad + vertSep) * i, vertRad);
                vertexWidgets.add(vertWidget);
                
                if(j > 0) {
                    VertexWidget leftWidget = vertexWidgets.get(vertexWidgets.size() - 2);
                    edgeWidgets.add(new EdgeWidget(leftWidget, vertWidget));
                }
                if(i > 0) {
                    VertexWidget topWidget = vertexWidgets.get((i - 1) * vertPerRow + j);
                    edgeWidgets.add(new EdgeWidget(topWidget, vertWidget));
                }
            }
        }
    }
    
    /**
     * Render panel
     */
    private class RenderPanel extends JPanel implements MouseListener {
        
        public RenderPanel() {
            addMouseListener(this);
        }
        
        @Override
        public void paint(Graphics g){
                update(g);
	}
        
        @Override
        public void update(Graphics g) {
            
            if(renderImg == null) {
                graphDim = getSize();
                renderImg = (BufferedImage)createImage(graphDim.width, graphDim.height);
                renderG = renderImg.createGraphics();
            }
            
            renderG.setPaint(Color.WHITE);
            renderG.fillRect(0, 0, graphDim.width, graphDim.height);
            
            render(renderG);
            
            g.drawImage(renderImg, 0, 0, this);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            onMouseClicked(e.getX(), e.getY());
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
    
    /**
     * Main render method
     */
    private void render(Graphics2D g) {
        for (EdgeWidget edgeWidget : edgeWidgets) {
            edgeWidget.render(g);
        }
        for (VertexWidget vertexWidget : vertexWidgets) {
            vertexWidget.render(g);
        }
    }
    
    private void onMouseClicked(int x, int y) {
        if(state == AppState.SOLVING)
            return;
        
        VertexWidget selectedWidget = null;
        for (VertexWidget vertexWidget : vertexWidgets) {
            if(vertexWidget.circle2D.contains(x, y)) {
                selectedWidget = vertexWidget;
                break;
            }
        }
        if(selectedWidget == null)
            return;
        
        if(state == AppState.NORMAL) {
            selectedVertexFrom = selectedWidget;
            selectedVertexFrom.state = RenderState.SELECTED;
            selectedVertexTo = null;
            state = AppState.FIST_VERTEX_SELECTED;
            
        } else if(state == AppState.SOLVED) {
            for (VertexWidget v : vertexWidgets) {
                v.state = RenderState.NORMAL;
            }
            for (EdgeWidget e : edgeWidgets) {
                e.state = RenderState.NORMAL;
            }
            selectedVertexFrom = selectedWidget;
            selectedVertexFrom.state = RenderState.SELECTED;
            selectedVertexTo = null;
            state = AppState.FIST_VERTEX_SELECTED;
            
        } else if(state == AppState.FIST_VERTEX_SELECTED) {
            if(!selectedWidget.equals(selectedVertexFrom)) {
                selectedVertexTo = selectedWidget;
                selectedVertexTo.state = RenderState.SELECTED;
                
                state = state.SOLVING;
                solve();
            }
        }
        
        
        
        renderPanel.repaint();
    }
    
    private void solve() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
        
        selectedVertexFrom.state = RenderState.SOLUTION;
        selectedVertexTo.state = RenderState.SOLUTION;
        state = state.SOLVED;
    }
    
    
    private class VertexWidget {
        public final Vertex vertex;
        public final Ellipse2D circle2D;
        public RenderState state;

        public VertexWidget(Vertex vertex, int x, int y, int r) {
            this.vertex = vertex;
            this.circle2D = new Ellipse2D.Double(x, y, r, r);
            this.state = RenderState.NORMAL;
        }
        
        public void render(Graphics2D g) {
            Color c = Color.BLUE;
            if(state == RenderState.SELECTED || state == RenderState.SOLUTION) {
                c = Color.YELLOW;
            }
            
            g.setPaint(c);
            g.fill(circle2D);
        }
    }
    
    private class EdgeWidget {
        public final Edge edge;
        public final VertexWidget from;
        public final VertexWidget to;
        public final Line2D line2D;
        public RenderState state;
        
        public EdgeWidget(VertexWidget from, VertexWidget to) {
            this.edge = new Edge(from.vertex, to.vertex);;
            this.from = from;
            this.to = to;
            this.line2D = new Line2D.Double(from.circle2D.getCenterX(), from.circle2D.getCenterY(), 
                    to.circle2D.getCenterX(), to.circle2D.getCenterY());
            this.state = RenderState.NORMAL;
        }
        
        public void render(Graphics2D g) {
            Color c = Color.BLACK;
            if(state == RenderState.SELECTED || state == RenderState.SOLUTION) {
                c = Color.YELLOW;
            }
            
            g.setPaint(c);
            g.draw(line2D);
        }
        
    }
    
}
