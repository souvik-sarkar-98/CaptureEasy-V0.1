package screen.ScreenRecorder;

/******

public class PrtScScreenshot {
	

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrtScScreenshot window = new PrtScScreenshot();
					CommonVariablesAndMethods.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public PrtScScreenshot() {
		initialize();
	}

	
	private void initialize() {
		
		CommonVariablesAndMethods.frame = new JFrame();
		Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
		CommonVariablesAndMethods.frame.setLocation(screensize.width-100,screensize.height/2+100);
		CommonVariablesAndMethods.frame.setAlwaysOnTop(true);
		CommonVariablesAndMethods.frame.setResizable(false);
		CommonVariablesAndMethods.frame.setBounds(100, 100, 200, 172);
		CommonVariablesAndMethods.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		CommonVariablesAndMethods.panel = new JPanel();
		CommonVariablesAndMethods.panel.setBackground(Color.WHITE);
		CommonVariablesAndMethods.panel.addMouseListener(new MouseAdapter() {
			@Override
		    public void mouseClicked(MouseEvent e) 
		    {
		    	if (CommonVariablesAndMethods.IsEmpty(CommonVariablesAndMethods.TempImageFolderPath) || CommonVariablesAndMethods.c > 0) 
		    		{
						CommonVariablesAndMethods.p=e.getClickCount();
						if (CommonVariablesAndMethods.p==1)
						{
							try {
									CommonVariablesAndMethods.captureScreen();
								} 
							catch (InvalidFormatException e1) 
							{
									e1.printStackTrace();
							}
						}
		    		}	
		    }
			
			@Override
		    public void mouseEntered(MouseEvent e) 
		    {	
		    	if ( ! CommonVariablesAndMethods.IsEmpty(CommonVariablesAndMethods.TempImageFolderPath) && CommonVariablesAndMethods.c == 0) 
		    	{
		    		CommonVariablesAndMethods.updateLabel("PLEASE WAIT","");
		    	}
		    } 
		    
		    // MouseListener Invoked Method
		    @Override
		    public void mouseExited(MouseEvent e) 
			{
		    	if ( ! CommonVariablesAndMethods.IsEmpty(CommonVariablesAndMethods.TempImageFolderPath) && CommonVariablesAndMethods.c == 0) {
		    		CommonVariablesAndMethods.updateLabel("ACTION PERFORMED","");
		    	}
		    }  
		    
		    // MouseListener Invoked Method
		    @Override
		    public void mousePressed(MouseEvent e) 
			{
		    	if ( ! CommonVariablesAndMethods.IsEmpty(CommonVariablesAndMethods.TempImageFolderPath) && CommonVariablesAndMethods.c == 0) {
		    		CommonVariablesAndMethods.updateLabel("PLEASE WAIT","");
		    	}
		    } 
		    
		    // MouseListener Invoked Method
		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	if ( ! CommonVariablesAndMethods.IsEmpty(CommonVariablesAndMethods.TempImageFolderPath) && CommonVariablesAndMethods.c == 0) {
		    		CommonVariablesAndMethods.updateLabel("PLEASE WAIT","");
		    	}
		    	
		    } 
		    	
		});
		CommonVariablesAndMethods.frame.getContentPane().add(CommonVariablesAndMethods.panel, BorderLayout.CENTER);
		
		CommonVariablesAndMethods.textArealabel = new JTextArea();
		CommonVariablesAndMethods.textArealabel.setText("b");
		CommonVariablesAndMethods.textArealabel.setWrapStyleWord(false);
		CommonVariablesAndMethods.textArealabel.setLineWrap(false);
		CommonVariablesAndMethods.textArealabel.setRows(1);
		CommonVariablesAndMethods.textArealabel.setMargin(new Insets(5, 5, 5, 5));
		CommonVariablesAndMethods.textArealabel.setForeground(Color.RED);
		CommonVariablesAndMethods.textArealabel.setFont(new Font("Monospaced", Font.BOLD, 16));
		CommonVariablesAndMethods.textArealabel.setBorder(UIManager.getBorder("TextArea.border"));
		CommonVariablesAndMethods.textArealabel.setEditable(false);
		
		CommonVariablesAndMethods.countLabel = new JTextArea();
		CommonVariablesAndMethods.countLabel.setColumns(12);
		CommonVariablesAndMethods.countLabel.setText("CAPTURED ("+CommonVariablesAndMethods.c+")");
		CommonVariablesAndMethods.countLabel.setLineWrap(true);
		CommonVariablesAndMethods.countLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
		CommonVariablesAndMethods.panel.add(CommonVariablesAndMethods.countLabel);
		
		CommonVariablesAndMethods.lblClipboardStatus = new JLabel("Clipboard Status :");
		CommonVariablesAndMethods.lblClipboardStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		CommonVariablesAndMethods.panel.add(CommonVariablesAndMethods.lblClipboardStatus);
		CommonVariablesAndMethods.textArealabel.setBackground(Color.WHITE);
		CommonVariablesAndMethods.textArealabel.setColumns(15);
		CommonVariablesAndMethods.panel.add(CommonVariablesAndMethods.textArealabel);
		
		CommonVariablesAndMethods.buttonPanel = new JPanel();
		CommonVariablesAndMethods.buttonPanel.setBackground(Color.WHITE);
		CommonVariablesAndMethods.frame.getContentPane().add(CommonVariablesAndMethods.buttonPanel, BorderLayout.SOUTH);
		
		CommonVariablesAndMethods.btnPause = new JButton("ACTION");
		CommonVariablesAndMethods.buttonPanel.add(CommonVariablesAndMethods.btnPause);
		CommonVariablesAndMethods.btnPause.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (CommonVariablesAndMethods.Runflag==true)
				{
					CommonVariablesAndMethods.Runflag=false;
					CommonVariablesAndMethods.updateLabel(Color.RED, "PAUSED");
					CommonVariablesAndMethods.button.setText("RESUME");
				}
				else if (CommonVariablesAndMethods.Runflag==false)
				{
					CommonVariablesAndMethods.Runflag=true;
					StringSelection stringSelection = new StringSelection(CommonVariablesAndMethods.Temp);
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents((Transferable) stringSelection, null);
					CommonVariablesAndMethods.button.setText("PAUSE");
				}
				
			}
			
			
			
		});
		
		CommonVariablesAndMethods.button = new JButton("RESUME");
		CommonVariablesAndMethods.button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if( CommonVariablesAndMethods.c!=0)
				{
					 CommonVariablesAndMethods.updateLabel("ACTION PERFORMED","");
					 CommonVariablesAndMethods.action=new Action(1);
					 CommonVariablesAndMethods.action.setVisible(true);
					 CommonVariablesAndMethods.action.textField.requestFocusInWindow();
				}
				else
				{
					try{CommonVariablesAndMethods.DeleteScreenshots(CommonVariablesAndMethods.TempImageFolderPath);}
					catch(FileNotFoundException e1){}
				}
				
			}
			
		});
		CommonVariablesAndMethods.buttonPanel.add(CommonVariablesAndMethods.button);
	}

}


******/