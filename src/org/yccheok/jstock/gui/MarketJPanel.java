/*
 * JStock - Free Stock Market Software
 * Copyright (C) 2010 Yan Cheng CHEOK <yccheok@yahoo.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package org.yccheok.jstock.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;
import org.yccheok.jstock.engine.Code;
import org.yccheok.jstock.engine.Country;
import org.yccheok.jstock.engine.Index;
import org.yccheok.jstock.engine.Market;
import org.yccheok.jstock.engine.Stock;
import org.yccheok.jstock.engine.Symbol;
import org.yccheok.jstock.internationalization.GUIBundle;

/**
 *
 * @author  yccheok
 */
public class MarketJPanel extends javax.swing.JPanel {

    /** Creates new form MarketJPanel */
    public MarketJPanel(Country country) {
        initComponents();
        
        this.country = country;        
        initAccordingToCountry(country);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        leftPanel = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());
        add(leftPanel, java.awt.BorderLayout.WEST);
    }// </editor-fold>//GEN-END:initComponents

    public void update(List<Market> markets) {
        final java.text.NumberFormat numberFormat = java.text.NumberFormat.getInstance();
        
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);

        for (Market market : markets) {
            final double _index = market.stock.getLastPrice();
            final double change = market.stock.getChangePrice();
            final Color color = Utils.getColor(change, 0.0);
            
            JLabel label = map.get(market.index.name());
            
            if (label == null) {
                continue;
            }
            
            label.setText(numberFormat.format(_index) + " (" + numberFormat.format(change) + ")");
            label.setForeground(color);
        }                
    }
    
    private void initAccordingToCountry(Country country) {
        List<Index> indices = org.yccheok.jstock.engine.Utils.getStockIndices(country);
        for (final Index index : indices) {
            JLabel name = new JLabel(index.toString() + " : ");
            leftPanel.add(name);
            JLabel value = new JLabel();
            value.setName(index.name());
            map.put(index.name(), value);
            value.setFont(Utils.getBoldFont(value.getFont()));
            leftPanel.add(value);

            // Install mouse handler.
            name.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    MarketJPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    MarketJPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    final Code code = index.code;
                    final JStock m = JStock.instance();
                    final Stock stock = org.yccheok.jstock.engine.Utils.getEmptyStock(code, Symbol.newInstance(code.toString()));
                    m.displayHistoryChart(stock);
                }
            });
        }
    }

    private String getValueLabel() {
        return MessageFormat.format(
            GUIBundle.getString("MainFrame_ValueLabel_template"),
            /*java.util.Currency.getInstance(Locale.getDefault()).getSymbol()*/"$"
        );
    }

    public Country getCountry() {
        return this.country;
    }
    
    private Country country = null;
    private Map<String, JLabel> map = new HashMap<String, JLabel>();
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JPanel leftPanel;
    // End of variables declaration//GEN-END:variables

}
